/*
    Copyright (c) 2017 Harriet Cooper

    This file is part of typemaps.

    Typemaps is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Typemaps is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with typemaps.  If not, see <http://www.gnu.org/licenses/>.
 */
package typemaps

import TypeMap._

import scala.reflect.ClassTag

sealed trait PrettyPrint[M <: TypeMap] {
  def apply(m: M): String
}

object PrettyPrint {
  implicit val tip: PrettyPrint[Tip] = new PrettyPrint[Tip] {
    override def apply(m: Tip): String = "Tip"
  }

  implicit def bin[KA, A, L <: TypeMap, R <: TypeMap](implicit a: ClassTag[KA],
                                                      l: PrettyPrint[L],
                                                      r: PrettyPrint[R]
                                                     ): PrettyPrint[Bin[KA, A, L, R]] =
    new PrettyPrint[Bin[KA, A, L, R]] {
      override def apply(m: Bin[KA, A, L, R]): String = {
        val sb = new StringBuilder()
        val bin = s"[$a -> ${m.a}]"
        sb.append(bin)
        sb.append("--")
        val len = bin.length
        def indent(): Unit = {
          var i = 0
          while (i < len) {
            sb.append(' ')
            i += 1
          }
        }

        val rs = r(m.r).lines // Non-empty
        sb.append(rs.next)
        rs.foreach { l =>
          sb.append('\n')
          indent()
          sb.append("| ")
          sb.append(l)
        }

        sb.append('\n')
        indent()
        sb.append("\\-")

        val ls = l(m.l).lines // Non-empty
        sb.append(ls.next)
        ls.foreach { l =>
          sb.append('\n')
          indent()
          sb.append("  ")
          sb.append(l)
        }

        sb.result()
      }
    }
}