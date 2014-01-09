<?php
$title = "Transform A to B";
require_once 'inc/header.php';
?>
<form action="upload.php" method="POST" enctype="multipart/form-data">
	<label for="file-input">XML file of Company A:</label><br />
	<input type="file" name="file-input" /><br />
	<input type="submit" value="Submit XML file" />
</form>

<?php require_once 'inc/footer.php'; ?>