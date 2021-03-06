package beditor.view;

import javax.swing.*;
import java.awt.*;
import beditor.model.*;

/**
* ItemPropertiesPan : a class to display and choose the properties of a tile
*
* @author Hugo PIGEON
* @version 1.1
*/

public class ItemPropertiesPan extends JPanel
{
	private EditorPan editorPan;
	private JLabel selectedSprite;
	private JRadioButton notAnItemRadio, keyRadio, lollipopRadio, doorRadio;

	private final String MESSAGE = "Selected item :";
	private final String NOT_AN_ITEM_RADIO = "Not an item";
	private final String KEY_RADIO = "Golden key";
	private final String LOLLIPOP_RADIO = "Lollipop";
	private final String DOOR_RADIO = "Door";

	/** Constructor which makes a new properties panel for the items
	* @param editorPan the EditorPan which contains this ItemPropertiesPan
	*/
	public ItemPropertiesPan(EditorPan editorPan)
	{
		this.setLayout(new GridLayout(6, 1));
		
		this.editorPan = editorPan;
		
		JLabel message = new JLabel(MESSAGE);
	
		this.selectedSprite = new JLabel(this.editorPan.getSelectedTile().getImage().getIcon());
		
		if(this.editorPan.getTilesetPan().getTileset().getItem(this.editorPan.getTilesetPan().getSelectedSpriteIndex()).getType() == ItemType.NOT_AN_ITEM)
			this.notAnItemRadio = new JRadioButton(NOT_AN_ITEM_RADIO, true);
		else
			this.notAnItemRadio = new JRadioButton(NOT_AN_ITEM_RADIO, false);
		
		if(this.editorPan.getTilesetPan().getTileset().getItem(this.editorPan.getTilesetPan().getSelectedSpriteIndex()).getType() == ItemType.KEY)
			this.keyRadio = new JRadioButton(KEY_RADIO, true);
		else
			this.keyRadio = new JRadioButton(KEY_RADIO, false);
		
		if(this.editorPan.getTilesetPan().getTileset().getItem(this.editorPan.getTilesetPan().getSelectedSpriteIndex()).getType() == ItemType.LOLLIPOP)
			this.lollipopRadio = new JRadioButton(LOLLIPOP_RADIO, true);
		else
			this.lollipopRadio = new JRadioButton(LOLLIPOP_RADIO, false);
			
		if(this.editorPan.getTilesetPan().getTileset().getItem(this.editorPan.getTilesetPan().getSelectedSpriteIndex()).getType() == ItemType.DOOR)
			this.doorRadio = new JRadioButton(DOOR_RADIO, true);
		else
			this.doorRadio = new JRadioButton(DOOR_RADIO, false);
	
		ButtonGroup itemGroup = new ButtonGroup();
		itemGroup.add(this.notAnItemRadio);
		itemGroup.add(this.keyRadio);
		itemGroup.add(this.lollipopRadio);
		itemGroup.add(this.doorRadio);
		
		JPanel messagePan = new JPanel();
		messagePan.add(message);
		JPanel notAnItemPan = new JPanel();
		notAnItemPan.add(this.notAnItemRadio);
		JPanel keyPan = new JPanel();
		keyPan.add(this.keyRadio);
		JPanel lollipopPan = new JPanel();
		lollipopPan.add(this.lollipopRadio);
		JPanel doorPan = new JPanel();
		doorPan.add(this.doorRadio);
		
		this.add(messagePan);
		this.add(selectedSprite);
		this.add(notAnItemPan);
		this.add(keyPan);
		this.add(lollipopPan);
		this.add(doorPan);
	}
	
	/** Gives the "not an item" radio
	* @return the "not an item" radio
	*/
	public JRadioButton getNotAnItemRadio()
	{
		return this.notAnItemRadio;
	}
	
	/** Gives the "key" radio
	* @return the "key" radio
	*/
	public JRadioButton getKeyRadio()
	{
		return this.keyRadio;
	}
	
	/** Gives the "lollipop" radio
	* @return the "lollipop" radio
	*/
	public JRadioButton getLollipopRadio()
	{
		return this.lollipopRadio;
	}
	
	/** Gives the "door" radio
	* @return the "door" radio
	*/
	public JRadioButton getDoorRadio()
	{
		return this.doorRadio;
	}
	
	/** Refreshes the display of this PropertiesPan
	*/
	public void revalidate()
	{
		if(this.editorPan != null && this.editorPan.getTilesetPan() != null)
		{
			this.selectedSprite.setIcon(this.editorPan.getSelectedTile().getImage().getIcon());
			
			if(this.editorPan.getTilesetPan().getTileset().getItem(this.editorPan.getTilesetPan().getSelectedSpriteIndex()).getType() == ItemType.NOT_AN_ITEM)
			{
				this.notAnItemRadio.setSelected(true);
				this.keyRadio.setSelected(false);
				this.lollipopRadio.setSelected(false);
				this.doorRadio.setSelected(false);
			}
			else if(this.editorPan.getTilesetPan().getTileset().getItem(this.editorPan.getTilesetPan().getSelectedSpriteIndex()).getType() == ItemType.KEY)
			{
				this.notAnItemRadio.setSelected(false);
				this.keyRadio.setSelected(true);
				this.lollipopRadio.setSelected(false);
				this.doorRadio.setSelected(false);
			}
			else if(this.editorPan.getTilesetPan().getTileset().getItem(this.editorPan.getTilesetPan().getSelectedSpriteIndex()).getType() == ItemType.LOLLIPOP)
			{
				this.notAnItemRadio.setSelected(false);
				this.keyRadio.setSelected(false);
				this.lollipopRadio.setSelected(true);
				this.doorRadio.setSelected(false);
			}
			else if(this.editorPan.getTilesetPan().getTileset().getItem(this.editorPan.getTilesetPan().getSelectedSpriteIndex()).getType() == ItemType.DOOR)
			{
				this.notAnItemRadio.setSelected(false);
				this.keyRadio.setSelected(false);
				this.lollipopRadio.setSelected(false);
				this.doorRadio.setSelected(true);
			}
		}
	
		super.revalidate();
	}
}
