<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>The server</title>
	</head>
	<body>
		<p> Hi ! Please connect you with pour user name and your password :</p>
		<form action="cible.php" method="POST">
			<p><label>User : <input type="text" name="user"/></p> <!--Ask the name of the user-->
			<p><label>Password : <input type="text" name="password"/></p>
			<p><input type="submit"/></p>
		</form>
	</body>
</html>