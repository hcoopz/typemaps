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

import shapeless.syntax.singleton._

object SingletonTags {
  val unit = 'unit.narrow
  val boolean = 'boolean.narrow
  val short = 'short.narrow
  val int = 'int.narrow
  val long = 'long.narrow
  val float = 'float.narrow
  val double = 'double.narrow
  val bigDecimal = 'bigDecimal.narrow
  val bigInt = 'bigInt.narrow
  val char = 'char.narrow
  val symbol = 'symbol.narrow
  val string = 'string.narrow
  val optionUnit = 'optionUnit.narrow
}