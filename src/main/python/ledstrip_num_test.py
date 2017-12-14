#!/usr/bin/python
import sys

number = int(sys.argv[1])

# Success Message to Screen
print("Number of Pixels: ", 9, " from ", number)  

file = open("ledstrip.txt","a")
file.write("Number of Pixels: " + "9" + " from " + str(number) + "\n")

sys.exit(109)