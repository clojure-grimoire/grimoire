  Create an indent at this point in the pretty printing stream. This defines how 
following lines are indented. relative-to can be either :block or :current depending 
whether the indent should be computed relative to the start of the logical block or
the current column position. n is an offset. 

This function is intended for use when writing custom dispatch functions.

Output is sent to *out* which must be a pretty printing writer.