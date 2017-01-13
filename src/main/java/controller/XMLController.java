package controller;

import java.awt.Rectangle;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import lombok.extern.log4j.Log4j;
import model.Agent;
import model.GridCell;
import model.Reward;
import view.GridView;

@Log4j
public class XMLController {

	public static int loadLayoutXML(String filePath, GridView gridView) {
		DocumentBuilderFactory factory;
		DocumentBuilder builder = null;

		GridCell gridCell = null;

		try {
			File inputFile = new File(filePath);
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();

			Document doc = builder.parse(inputFile);
			doc.getDocumentElement().normalize();

			String root = doc.getDocumentElement().getNodeName();

			if (!root.equals("GridCell")) {
				return -1;
			}

			// Set Agent
			int position = Integer.parseInt(doc.getElementsByTagName("Agent").item(0).getFirstChild().getTextContent());
			int power = Integer
					.parseInt(doc.getElementsByTagName("MazeProperties").item(0).getLastChild().getTextContent());

			Agent singleAgent = new Agent(position);
			singleAgent.setPower(power);

			gridView.setSingleAgent(singleAgent);

			// get total count of rows
			int rowCount = Integer
					.parseInt(doc.getElementsByTagName("MazeProperties").item(0).getFirstChild().getTextContent());
			int colCount = Integer
					.parseInt(doc.getElementsByTagName("MazeProperties").item(0).getLastChild().getTextContent());
			log.info(rowCount + " " + colCount);
			gridCell = new GridCell(rowCount, colCount);

			
			
			// First parse Rectangle Array
			Element currentNode = (Element) doc.getElementsByTagName("Rectangle").item(0);
			NodeList nList = currentNode.getChildNodes();
			Rectangle rect[] = new Rectangle[rowCount * colCount];

			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element innerElement = (Element) nNode;
					NodeList innerEleList = innerElement.getChildNodes();
					if (innerEleList.getLength() == 4) {
						// X co-ordinate
						Element firstTag = (Element) innerEleList.item(0);
						int x = Integer.parseInt(firstTag.getTextContent());

						// Y co-ordinate
						Element secondTag = (Element) innerEleList.item(1);
						int y = Integer.parseInt(secondTag.getTextContent());

						Element thirdTag = (Element) innerEleList.item(2);
						int w = Integer.parseInt(thirdTag.getTextContent());

						Element fourthTag = (Element) innerEleList.item(3);
						int h = Integer.parseInt(fourthTag.getTextContent());

						rect[i] = new Rectangle(x, y, w, h);
					}

				}
			}
			gridCell.setMazeCell(rect);

			// Parse Reward
			currentNode = (Element) doc.getElementsByTagName("Reward").item(0);
			nList = currentNode.getChildNodes();

			HashMap<Integer, Reward> reward= new HashMap<>();
			for(int i = 0 ; i < nList.getLength() ; i++){
				NodeList rewardList = nList.item(i).getChildNodes();
				String[] split = nList.item(i).getNodeName().split("_");
				int rowNo = Integer.parseInt(split[1]);
				Element rewardType = (Element) rewardList.item(0);
				Element rewardValue = (Element) rewardList.item(1);
				reward.put(rowNo, new Reward(rewardType.getTextContent(),Double.parseDouble(rewardValue.getTextContent())));

			}
			gridCell.setReward(reward);

			// Parse Visibility
			currentNode = (Element) doc.getElementsByTagName("Visibility").item(0);
			nList = currentNode.getChildNodes();
			boolean visible[] = new boolean[rowCount * colCount];
			Arrays.fill(visible, false);
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				visible[i] = Boolean.parseBoolean(nNode.getTextContent());
			}
			gridCell.setVisibility(visible);

			gridView.setGridCell(gridCell);
			gridView.setMazeCell(gridCell.getMazeCell());
			gridView.setVisibility(gridCell.getVisibility());
			gridView.mazeSetFlag = true;
			gridView.mazeFinalized = true;
			AgentActions.setMazeLength(gridCell.getRows());
			AgentActions.setMazeWidth(gridCell.getCols());
			AgentActions.setGridCell(gridCell);
			
			System.out.println("Event value: "+gridView.getGridCell().getCellEvents().isEmpty());
			System.out.println("Event value: "+gridView.getGridCell().getCellProperty().isEmpty());
			System.out.println("Event value: "+gridView.getGridCell().getReward().isEmpty());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void saveLayoutXML(GridCell gridCell, String filePath, Agent singleAgent) {
		DocumentBuilderFactory factory;
		DocumentBuilder builder = null;
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();

			Document doc = builder.newDocument();

			// Append root node as Mazecell
			Element rootElement = doc.createElement("GridCell");
			doc.appendChild(rootElement);

			// Save the Generic Properties
			Element mazeProperties = doc.createElement("MazeProperties");
			rootElement.appendChild(mazeProperties);
			// Saving rows
			Element rows = doc.createElement("Rows");
			rows.appendChild(doc.createTextNode(gridCell.getRows() + ""));
			mazeProperties.appendChild(rows);

			Element cols = doc.createElement("Cols");
			cols.appendChild(doc.createTextNode(gridCell.getCols() + ""));
			mazeProperties.appendChild(cols);

			// Adding Agent information
			Element agentInfo = doc.createElement("Agent");
			rootElement.appendChild(agentInfo);

			Element pos = doc.createElement("Position");
			pos.appendChild(doc.createTextNode(singleAgent.getPos() + ""));
			agentInfo.appendChild(pos);

			Element power = doc.createElement("Power");
			power.appendChild(doc.createTextNode(singleAgent.getPower() + ""));
			agentInfo.appendChild(power);

			// Append Rectangle Properties
			Element rectangle = doc.createElement("Rectangle");
			rootElement.appendChild(rectangle);

			for (int i = 0; i < gridCell.getMazeCell().length; i++) {
				Element rowNo = doc.createElement("Row_" + i);

				Element recX = doc.createElement("recX");
				recX.appendChild(doc.createTextNode(gridCell.getMazeCell()[i].x + ""));

				Element recY = doc.createElement("recY");
				recY.appendChild(doc.createTextNode(gridCell.getMazeCell()[i].y + ""));

				Element recW = doc.createElement("recW");
				recW.appendChild(doc.createTextNode(gridCell.getMazeCell()[i].width + ""));

				Element recH = doc.createElement("recH");
				recH.appendChild(doc.createTextNode(gridCell.getMazeCell()[i].height + ""));

				rowNo.appendChild(recX);
				rowNo.appendChild(recY);
				rowNo.appendChild(recW);
				rowNo.appendChild(recH);
				rectangle.appendChild(rowNo);
			}

			// Append Properties of Rewards
			Element reward = doc.createElement("Reward");
			rootElement.appendChild(reward);

			for (Integer i : gridCell.getReward().keySet()) {
				Element rewardType = doc.createElement("RewardType");
				Element rewardValue = doc.createElement("RewardValue");
				Element rowNo = doc.createElement("Row_" + i);
				rowNo.appendChild(rewardType);
				rowNo.appendChild(rewardValue);
				rewardType.appendChild(doc.createTextNode(gridCell.getReward().get(i).getType()));
				rewardValue.appendChild(doc.createTextNode(gridCell.getReward().get(i).getValue()+""));
				reward.appendChild(rowNo);
			}

			// Append Properties of Visibility
			Element visibility = doc.createElement("Visibility");
			rootElement.appendChild(visibility);

			for (int i = 0; i < gridCell.getMazeCell().length; i++) {
				Element rowNo = doc.createElement("Row_" + i);
				rowNo.appendChild(doc.createTextNode(gridCell.getVisibility()[i] + ""));
				visibility.appendChild(rowNo);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);
			log.info("File saved!");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
