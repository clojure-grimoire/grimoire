<div id="examples" markdown="1">
## Examples

<ul class="exd">
{% for post in site.pages %}
  {% if post.tags contains page.var %}
    <div class="ex">
      {{ post }}
    </div>
  {% endif %}
{% endfor %}
</ul>
</div>
