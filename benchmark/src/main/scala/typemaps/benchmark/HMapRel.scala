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
package typemaps.benchmark

class HMapRel[A, B]

object HMapRel {
  private[this] def mkRel[A](a: A): HMapRel[A, Int] = new HMapRel[A, Int]

  implicit val unit = mkRel(SingletonTags.unit)
  implicit val boolean = mkRel(SingletonTags.boolean)
  implicit val short = mkRel(SingletonTags.short)
  implicit val int = mkRel(SingletonTags.int)
  implicit val long = mkRel(SingletonTags.long)
  implicit val float = mkRel(SingletonTags.float)
  implicit val double = mkRel(SingletonTags.double)
  implicit val bigDecimal = mkRel(SingletonTags.bigDecimal)
  implicit val bigInt = mkRel(SingletonTags.bigInt)
  implicit val char = mkRel(SingletonTags.char)
  implicit val symbol = mkRel(SingletonTags.symbol)
  implicit val string = mkRel(SingletonTags.string)
  implicit val optionUnit = mkRel(SingletonTags.optionUnit)
}
