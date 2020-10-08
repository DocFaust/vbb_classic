<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:m="http://www.docfaust.de/vbb">
	<xsl:output method="html" encoding="utf-8" indent="yes" doctype-public="XSLT-compat"/>
	<xsl:template match="/">
		<html>
		<head>
			   <link rel="stylesheet" type="text/css" href="dxsl.css"/>
			   <title>Messages Configuration</title>
			   </head>
			<body>
				<h1>Messages Configuration</h1>
				<table border="1">
					<tr bgcolor="#9acd32">
						<th style="text-align:left">Nr</th>
						<th style="text-align:left">Code</th>
						<th style="text-align:left">Severity</th>
						<th style="text-align:left">Summary</th>
						<th style="text-align:left">Detail</th>
					</tr>
					<xsl:for-each select="m:messages/m:message">
						<tr>
							<td>
								<xsl:value-of select="position()" />
							</td>
							<td>
								<xsl:value-of select="m:code" />
							</td>
							<td>
								<xsl:value-of select="m:severity" />
							</td>
							<td>
								<xsl:value-of select="m:summary" />
							</td>
							<td>
								<xsl:value-of select="m:detail" />
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>

	</xsl:template>
</xsl:stylesheet>