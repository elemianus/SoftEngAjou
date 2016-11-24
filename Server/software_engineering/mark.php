<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>The server</title>
	</head>
	<body>
		<?php if(isset($_POST["user"]) && isset($_POST["password"]))
			{
				$name=$_POST["user"];
				$pass=$_POST["password"];
					if($name == "Florent" && $pass == "password")
					{
						echo "Hi " . htmlspecialchars($_POST['user']) . "!"; ?>
						<p><select name ="choix">
							<option value="Temperature" selected="selected">Temperature</option>
							<option value="HistoryTemperature">Temperature History</option>
						</select></p> 
						<p> Come back to the <a href="form.php"> form page</a>.</p> <?php
					}
					else 
					{
						echo "<p>Wrong username ou password. Try again.</p>"; ?>
						<p> Come back to the <a href="form.php"> form page</a>.</p>
					<?php
					}
			}
			else
			{
				 echo "<p>Error</p>";
			}
			 ?>
	</body>
</html>