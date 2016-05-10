# Level format documentation

This is the documentation for the SOPA level format (.lv).

## File markup
### Encoding
All characters are encoded in UTF-8.
### Structure
The File consists of 2 blocks.
The blocks are seperated with a line consisting of a '#'.
The last line has to be empty.

#### Information block
Every line of the information block stands for 1 information.
The more lines there are between the first line and the #-line, the more information is defined:


1.  ID: This line defines the Level-ID (has to be the same as the file name)
2.  MinimalMoves: This line defines the minimal moves, needed to solve the level
3.  TubesCount: This line defines the count of the tubes used in the level

#### Field

The field block describes the game field of the level.
Every character stands for one tile.
Every line has to have the same width.

Every character of the first line, the last line, the left and the right corner has to be an 's', 'f' or 'n'.
##### s
Is the start tile. Use it just once.
##### f
Is the finish tile. Use it just once.
##### n
Is the border tile.

##### i
Is a tube.
```
oXo
oXo
oXo
```
##### g
Is a tube.
```
oXo
XXo
ooo
```
##### a
Is a tube.
```
ooo
XXX
ooo
```
##### c
Is a tube.
```
ooo
XXo
oXo
```
##### e
Is a tube.
```
oXo
oXX
ooo
```
##### u
Is a tube.
```
ooo
oXX
oXo
```


All free places have to be filled with an o.
#### o
```
ooo
ooo
ooo
```
### Example
Content of file level.2 (note the blank line at the end):
```
2
1
5
\#
nnnnnn
noooon
naoouf
nooion
nooeas
nnnnnn

```
