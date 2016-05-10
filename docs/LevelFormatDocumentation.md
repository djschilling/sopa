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
0X0   
0X0   
0X0
##### g
Is a tube.   
0X0   
XX0   
000
##### a
Is a tube.   
000   
XXX   
000
##### c
Is a tube.   
000   
XX0   
0X0
##### e
Is a tube.   
0X0   
0XX   
000
##### u
Is a tube.   
0=0   
0XX   
0X0


All free places have to be filled with an o.
#### o
000   
000   
000   
### Example
START_OF_FILE "level.2"   
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
 
END_OF_FILE