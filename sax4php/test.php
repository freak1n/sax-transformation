<?php // header('Content-type: text/xml');
include_once('Sax4PHP.php');

class MySaxHandler extends DefaultHandler {
  private $products = array();

  function startElement($name, $attr)
  {

    if ($name == 'product')
    {


    }
  }

  function endElement($name)
  {
    if ($name = 'product')
    {

    }
  }

  function startDocument()
  {
  	echo "<products>\n";
  }

  function endDocument()
  {
  	echo "</products>\n";
  }

  function characters($strings)
  {
    // echo $strings;
  }

  function node($data)
  {

  }

  function get_products_array()
  {
    return $this->products;
  }
}

$xml = file_get_contents('example_A.xml');
$sax = new SaxParser(new MySaxHandler());

try
{
	$sax->parse($xml);
}
catch(SAXException $e)
{
	echo "\n",$e;
}
catch(Exception $e)
{
	echo "Default exception >>", $e;
}

echo '<pre>';
var_dump($sax->get_products_array());