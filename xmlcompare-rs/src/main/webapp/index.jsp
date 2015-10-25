<html>
<head>
<title>XmlCompare</title>
</head>
<body>
	<p>
		XmlCompare Home Page: Test <a
			href="http://localhost:8080/xmlcompare-rs/xmlcompare">here</a>
	</p>
	<h4>XmlCompare web service</h4>
	<div
		style="width: 400px; border: 1px solid blue; padding: 20px; text-align: center;">
		<form method="post"
			action="http://localhost:8080/xmlcompare-rs/xmlcompare"
			enctype="multipart/form-data">
			<table align="center" border="1" bordercolor="black" cellpadding="0"
				cellspacing="0">
				<tr>
					<td>Select File 1 :</td>
					<td><input type="file" name="controlFile" size="100" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Upload File" /></td>
					<td><input type="reset" value="Reset" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>