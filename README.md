SqlMaker
===========

A tiny DSL for writing sql queries

Tables (table name and column names) are kept in the code as enums

The current implementation has the convention of an enum with the values as column names, and a field called tableName.

Todo
===========
* date insert
* inner join
* outer join
* update x set y = z
* update x where y = z where a = b
* SELECT a1 FROM t1 INNER JOIN t2 ON t1.SomeID = t2.SomeID
* aliasing tables on join (will this be needed?)
