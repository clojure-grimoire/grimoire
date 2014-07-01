## Contributing

Each function, variable and macro has three files:

<pre>
/_include/$CLOJURE-VERSION/$NAMESPACE/$SYMBOL/docs.md
/_include/$CLOJURE-VERSION/$NAMESPACE/$SYMBOL/source.md
/_include/$CLOJURE-VERSION/$NAMESPACE/$SYMBOL/examples.md
</pre>

Note that symbols are munged via `(-> sym name clojure.core/munge clojure.string/lower)`

The per-symbol documentation is then generated with a template equivalent to the following:

<pre>
{% raw %}
---
layout: fn
namespace: $NAMESPACE
symbol: $SYMBOL
---

# [$NAMESPACE](../)/$SYMBOL

{% include $CLOJURE-VERSION/$NAMESPACE/$SYMBOL/docs.md %}
{% include $CLOJURE-VERSION/$NAMESPACE/$SYMBOL/examples.md %}
{% include $CLOJURE-VERSION/$NAMESPACE/$SYMBOL/src.md %}
{% endraw %}
</pre>

These template files are not intended for editing and no changes will
be taken to template files to add conted. Changes may be taken to add
additional template files, say for commentary or comments however
these changes must be generic over every symbol. New content, whether
in the form of examples or historical details, must be submitted as
changes to the data source files in `_include/`.

To add an example to your favorite function, simply fork the project
[here on GitHub](https://github.com/arrdem/grimoire), add your
favorite example to the oldest Clojure API version to which it applies
and submit a pull request. Examples should provide a sequence of top
level forms followed by a comment detailing any side-effects, and a
`(pr)` of the return value prefixed with `=> `.
