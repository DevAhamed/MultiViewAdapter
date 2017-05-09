<?xml version="1.0"?>
<recipe>

	<instantiate from="src/app_package/Binder.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${binderName}.java" />
  <instantiate from="res/layout/item_view.xml"
                   to="${escapeXmlAttribute(resOut)}/layout/${item_layout}.xml" />

	<open file="${srcOut}/${modelName}Adapter.java"/>
</recipe>