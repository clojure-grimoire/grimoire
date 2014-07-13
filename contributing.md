---
layout: page
title: Contributing
---

Grimoire was designed to be built and maintained by taking changes in
the form of pull requests, and is hosted
[here on GitHub](https://github.com/arrdem/grimoire).

### File structure

Each function, variable and macro and special form has at least three
files:

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

### Writing Examples

Grimoire "inherits" examples from one version of Clojure to the
next. The examples for `[org.clojure/clojure "1.6"]` include the
examples for `[org.clojure/clojure "1.5"]`. The
`[org.clojure/clojure "1.5"]` include `[org.clojure/clojure "1.4"]`
and soforth. As described in [contributing](contributing.md) these
examples are stored in the include directories related to their
specific Clojure versions.

For Grimoire `0.2.X` Examples should be formatted as follows:

<pre>
### Example $N
[permalink](#example-$N)
{% highlight clojure %}
{% raw %}
user=> (- 1)
-1
{% endraw %}
{% endhighlight %}
</pre>

### Building & Running Grimoire

Grimoire is built as a Clojure template generator which uses
[jekyll](http://jekyllrb.com/) to build generated templates to HTML.

To run a local instance of Grimoire, clone the repository and run
`jekyll serve`.

If you have changes to contribute, use `make test` which will rebuild
the entire site including the cheat sheet and finally `jekyll serve`
the result. If you have some more fine grained need, such as only
rebuilding the template files or only rebuilding the cheatsheet, check
the makefile. There's probabaly a make target in there for whatever
you need.

**WARNING**: Grimoire's build program is _lazy_. If it detects that a
file already exists, it won't write it. When making changes to the
template system, I suggest falsifying the checks for file existance
where appropriate and rebuilding. Be careful not to commit these
changes, as we won't take patches which clobber everything to rebuild
the site.

### Submitting changes

Rather than updating the documentation or examples in the formatted
files, changes for formatting and content should be applied to the
individual template files in `_include/`.

Changes to the template files should be applied to the generation
script and the site should be rebuilt with the resulting changes to
the rewritten templates.
