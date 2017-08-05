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