# DGChallenge
## Approach
1. All the fragments are loaded from file and stored into a set in the form of pairs (fragment; length of fragment / 2), since all the fragments can be of different lengths and have to overlap for at least of half of it
2. An arbitrary element from a set gets picked, which is not marked as the last one (it's denoted as -1 as a second element of the pair)
3. For all the elements in the set that overlap with the chosen element the problem is solved recursively with merged fragments added to the set and individual removed

## Implementation
I started playing with the problem in python and finished the python solution first. 

Since I used essentially recursive solution with immutable sets (I am creating new set with every recursion call) I decided to implement it with Scala, cause Scala sets are immutable and should have efficient memory sharing. Also it was fun converting file with fragments into sequence of pairs in functional style

## How to run 
Python solution is easy to run: solution.py filewithfragments

Main scala file is /scala/src/main/scala/Main.scala
Scala solution should be compiled with sbt: sbt package
then run something like that scala -cp  target/scala-2.10/classes/ Main  filewithfragments

## Further optimizaions
Solution has complexity O(n^2*m) where n is number of fragments and m - potential length of fragment. 
It can be optimized in part where all the fragments are searched for the ones that contain part of the chosen fragment.To optimize that Knuth-Morris-Pratt algorithm can be used, cause the part of the string that's being searched can be preprocessed once and then used fot all the searched. In order to not to overcomplicate the solution I used built-in functions to find the string. 



