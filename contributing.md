---
layout: page
title: Contributing
---

Grimoire was designed to be build and maintained by taking changes in
the form of pull requests, and is hosted
[here on GitHub](https://github.com/arrdem/grimoire).

### File structure

Each function, variable and macro has three files:

<pre>
/_include/$CLOJURE-VERSION/$NAMESPACE/$SYMBOL/docs.md
/_include/$CLOJURE-VERSION/$NAMESPACE/$SYMBOL/source.md
/_include/$CLOJURE-VERSION/$NAMESPACE/$SYMBOL/examples.md
</pre>

The per-symbol documentation is then generated with a template
equivalent to the following:

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

Namespaces and Clojure releases also have equivalent files.

### Submitting changes

Rather than updating the documentation or examples in the formatted
files, changes for formatting and content should be applied to the
individual template files in `_include/`.

Changes to the template files should be applied to the generation
script and the site should be rebuilt with the resulting changes to
the rewritten templates.
