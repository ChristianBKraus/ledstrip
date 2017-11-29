#!/usr/bin/python
import sys

# Read command line arguments
pin = int(sys.argv[1])
red = int(sys.argv[2])
blue = int(sys.argv[3])
green = int(sys.argv[4])

# Success Message to Screen
print("Set Color of Pin", pin, "to RGB", red, "/", blue, "/", green)  

file = open("ledstrip.txt","a")
file.write("Set Color of Pin " + str(pin) + " to RGB " + str(red) + "/" + str(blue) + "/" + str(green) + "\n")