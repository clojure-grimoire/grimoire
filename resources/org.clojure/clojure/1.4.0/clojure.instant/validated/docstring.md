  Return a function which constructs and instant by calling constructor
after first validting that those arguments are in range and otherwise
plausible. The resulting function will throw an exception if called
with invalid arguments.