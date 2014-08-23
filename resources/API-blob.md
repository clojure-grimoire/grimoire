# Grimoire Documentation Blob Format

A "blob" is a zip file with the following structure. All files listed
must by convention exist, but they may be empty.

```
/artifactId.txt
/groupId.txt
/version.txt
/releaseNotes.md
/namespaces.txt
/ns/$NAMESPACE/symbols.txt
/ns/$NAMESPACE/nsNotes.md
/ns/$NAMESPACE/sym/$SYMBOL/type.txt
/ns/$NAMESPACE/sym/$SYMBOL/arities.txt
/ns/$NAMESPACE/sym/$SYMBOL/docstring.txt
/ns/$NAMESPACE/sym/$SYMBOL/extended-docstring.md
/ns/$NAMESPACE/sym/$SYMBOL/source.clj
/ns/$NAMESPACE/sym/$SYMBOL/related.txt
/ns/$NAMESPACE/sym/$SYMBOL/examples.txt
/ns/$NAMESPACE/sym/$SYMBOL/ex/$EXAMPLE_ID.clj
```

Blobs are packages containing all the requisite information for
Grimoire to format and display documentation for a set of namespaces
and symbols.

## Files

**artifactID.txt** is a string, being the Maven artifact ID of the
  artifact documented by the blob.

**groupId.txt** is a string, being the Maven group ID of the artifact
  documented by the blob.

**version.txt** is a string, being the Maven version of the artifact
  documented by this blob.

**releaseNotes.md** is markdown formatted release notes for the Maven
  artifact documented by this blob.

**namespaces.txt** is a newline deliminated list of all namespaces in
  the artifact. Every namespace in this list must exist as a
  subdirectory of the `/ns/` dir.

**nsNotes.md** is markdown formatted release notes and/or commentary
  on the namespace in which the notes reside.

**symbols.txt** is a newline deliminated list of all symbols in a
  namespace of the artifact. For every symbol in this list, a
  `/ns/$NAMESPACE/sym/$SYMBOL/` directory must exist and be populated.

**type.txt** is a string, one of `#{"macro", "var", "fn", "special"}`.
  "macro" is reserved for Clojure macros, "var" is reserved for
  dynamic vars, "fn" is reserved for functions and "special" is
  reserved for compiler special forms such as `def` and `.`.
  
**artities.txt** is a string, expected to be a newline deliminated
  list of Clojure arity vectors.

**docstring.txt** is 
