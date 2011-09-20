package mapeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TileSetHandler {
	private Document doc;
	private Element root;
	private int id;
	
//	/**
//	 * Creates a new DOM document and sets the tile dimensions and blocks as
//	 * attributes.
//	 */
//	public TileSetHandler() {
//		try {
//			newDOMDoc();
//			root = doc.createElement("tile_set");
//			doc.appendChild(root);
//			
//			//Add the dimensions of the tiles at the start of the doc
//			root.setAttribute("dimension", CData.tileSize + "");
//			
//			//Add dimensions of blocks in a tile
//			root.setAttribute("block_dimension", CData.TILE_BLOCKS + "");
//			 
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		}
//	}

	private void newDOMDoc() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();

		doc = parser.newDocument();
	}

	/**
	 * 	Adds a tile with an attribute ID. The ID is dependent on how many tiles
	 * that have been added. 
	 * The tile has an element for the file name and one element for each of the
	 * different tile states. The boolean values of the tile states are represented
	 * with ones and zeros. 
	 * Nothing happens if the DOM doc or the root element is null.
	 * 
	 * @param fileName The file name of the bitmap
	 * @param tileStates All the states of the tile. See Tile.getTileStates()
	 * @return returns false if the tile could not be added.
	 */
//	public boolean addTile(String fileName, boolean[][][] tileStates){
//		if(root == null || doc == null) return false;
//		Element tile = doc.createElement("tile");
//		tile.setAttribute("id", id + "");
//		root.appendChild(tile);
//		id++;
//		
//		Element file = doc.createElement("file");
//		Text fileVal = doc.createTextNode(fileName);
//		file.appendChild(fileVal);
//		tile.appendChild(file);
//		
//		for (int i = 0; i < CData.moveTypes.length; i++) {
//			//Create the element
//			Element state = doc.createElement("state");
//			tile.appendChild(state);
//			//Set the name of the state
//			state.setAttribute("name", CData.moveTypes[i]);
//			//Calculate the value
//			String value = parseTileStates(tileStates, i);
//			Text stateVal = doc.createTextNode(value);
//			//Append value
//			state.appendChild(stateVal);
//		}
//		
//		return true;
//	}

//	private String parseTileStates(boolean[][][] tileStates, int k) {
//		StringBuffer sb = new StringBuffer();
//		int val;
//		for (int i = 0; i < CData.TILE_BLOCKS; i++) {
//			for (int j = 0; j < CData.TILE_BLOCKS; j++) {
//				val = tileStates[i][j][k] ? 1 : 0; 
//				sb.append(val + "");
//			}
//		}
//		return sb.toString();
//	}
	
	public void saveToFile(String fileName){
		
		TransformerFactory xf = TransformerFactory.newInstance();
		xf.setAttribute("indent-number", new Integer(2));

		Transformer xformer;
		try {
			
			xformer = xf.newTransformer();
			xformer.setOutputProperty(OutputKeys.METHOD, "xml");
			xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			xformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			
			File file = new File(".", fileName);
			OutputStream os = new FileOutputStream(file);

			Result result = new StreamResult(new OutputStreamWriter(os,
			"UTF-8"));

			xformer.transform(new DOMSource(doc), result);
			
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}