/*
 * Group #: 79
 * 
 * Kishan Patel (kp644)
 * Neal D Patel (ndp73)
 * 
 */

package songlib.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SongLibController {
	
	@FXML 
	private TableView<Song> tableView;
	
	private ObservableList<Song> obsList;
	
	@FXML
	private TableColumn<Song, String> song;
	
	@FXML
	private TableColumn<Song, String> artist;
	
	@FXML
	private TextField addArtist;
	
	@FXML 
	private TextField addSong;
	
	@FXML
	private TextField addAlbum;
	
	@FXML
	private TextField addYear;
	
	@FXML
	private TextField editArtist;
	
	@FXML 
	private TextField editSong;
	
	@FXML
	private TextField editAlbum;
	
	@FXML
	private TextField editYear;
	
	@FXML
	private Button addButton;
	
	@FXML
	private Button editButton;
	
	@FXML
	private Button deleteButton;
	
	@FXML 
	private Text songDisplay;
	
	@FXML
	private Text artistDisplay;
	
	@FXML
	private Text albumDisplay;
	
	@FXML
	private Text yearDisplay;
	
	private void display(int index) {
		tableView.getSelectionModel().select(index);
		songDisplay.setText("Song: " + tableView.getItems().get(index).getSong().toString());
		albumDisplay.setText("Album: " + tableView.getItems().get(index).getAlbum().toString());
		yearDisplay.setText("Year: " + tableView.getItems().get(index).getYear().toString());
		artistDisplay.setText("Artist: " + tableView.getItems().get(index).getArtist().toString());
	}
	
	private void checkTable(int selected) {
		if(tableView.getItems().size() > 0) {
			if(tableView.getItems().size() == selected) {
				tableView.getSelectionModel().select(selected-1);
				display(selected-1);
			} else {
				tableView.getSelectionModel().select(selected+1);
				display(selected);
			}
		} else {
			songDisplay.setVisible(false);
			albumDisplay.setVisible(false);
			yearDisplay.setVisible(false);
			artistDisplay.setVisible(false);
			editButton.setDisable(true);
			deleteButton.setDisable(true);
		}
	}
	
	private void compareSort() {
		Comparator<? super Song> compareSong = new Comparator<Song>() {
			@Override
			public int compare(Song s1, Song s2) {
				if(s1.getSong().equals(s2.getSong())) {
					return s1.getArtist().compareToIgnoreCase(s2.getArtist());
				} else {
					return s1.getSong().compareToIgnoreCase(s2.getSong());
				}

			}
		};
		Collections.sort(obsList, compareSong);
	}
	
	@FXML
	private void addClicked(ActionEvent event) throws IOException {
		if(addButton.getText().toString().equals("Add Song")) {
			addSong.setVisible(true);
			addArtist.setVisible(true);
			addAlbum.setVisible(true);
			addYear.setVisible(true);
			addButton.setText("Save or Cancel");
			deleteButton.setDisable(true);
			editButton.setDisable(true);
		} else if (addButton.getText().toString().equals("Save or Cancel")){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("ADD SONG");
			alert.setHeaderText("Confirmation");
			alert.setContentText("Are you sure you want to add this song to the library?");
			Optional<ButtonType> btn = alert.showAndWait();
			if(btn.get() == ButtonType.CANCEL) {
				
			} else if(btn.get() == ButtonType.OK) {
				int count = 0;
				int i;
				for(i=0;i<obsList.size();i++) {
					if(addSong.getText().toString().equalsIgnoreCase(tableView.getItems().get(i).getSong().toString()) && addArtist.getText().toString().equalsIgnoreCase(tableView.getItems().get(i).getArtist().toString())) {
						Alert error = new Alert(AlertType.ERROR);
						error.setTitle("Error");
						error.setHeaderText("Error on add");
						error.setContentText("This song and artist is already in the library");
						error.showAndWait();
						count = 1;
						break;
					} 
				}
				if(count == 0 && !addSong.getText().toString().equals("") && !addArtist.getText().toString().equals("")) {
					if(songDisplay.isVisible() == false) {
						songDisplay.setVisible(true);
						albumDisplay.setVisible(true);
						yearDisplay.setVisible(true);
						artistDisplay.setVisible(true);
					}
					obsList.addAll(new Song(addSong.getText().toString(), addArtist.getText().toString(), addAlbum.getText().toString(), addYear.getText().toString()));
					compareSort();
					write();
					for(i=0;i<obsList.size();i++) {
						if(addSong.getText().toString().equalsIgnoreCase(tableView.getItems().get(i).getSong().toString()) && addArtist.getText().toString().equalsIgnoreCase(tableView.getItems().get(i).getArtist().toString())) {
							tableView.getSelectionModel().select(i);
							display(i);
							break;
						} 
					}
				} else if (addSong.getText().toString().equals("") || addArtist.getText().toString().equals("")) {
					Alert error = new Alert(AlertType.ERROR);
					error.setTitle("Error");
					error.setHeaderText("Missing Information");
					error.setContentText("Song and/or Artist is empty");
					error.showAndWait();
				}
			}
			addArtist.setVisible(false);
			addSong.setVisible(false);
			addAlbum.setVisible(false);
			addYear.setVisible(false);
			addSong.setText("");
			addArtist.setText("");
			addAlbum.setText("");
			addYear.setText("");
			addButton.setText("Add Song");
			deleteButton.setDisable(false);
			editButton.setDisable(false);
		}
	}
	
	@FXML
	private void listClicked(MouseEvent event) throws IOException {
		int value = tableView.getSelectionModel().getSelectedIndex();
		if(value >= 0) {
			display(value);
		}
	}
	
	@FXML
	private void editClicked(ActionEvent event) throws IOException {
		if(editButton.getText().toString().equals("Edit Song")) {
			editSong.setVisible(true);
			editArtist.setVisible(true);
			editAlbum.setVisible(true);
			editYear.setVisible(true);
			editButton.setText("Save or Cancel");
			deleteButton.setDisable(true);
			addButton.setDisable(true);
			tableView.setDisable(true);
			editSong.setText(tableView.getSelectionModel().getSelectedItem().getSong().toString());
			editArtist.setText(tableView.getSelectionModel().getSelectedItem().getArtist().toString());
			editAlbum.setText(tableView.getSelectionModel().getSelectedItem().getAlbum().toString());
			editYear.setText(tableView.getSelectionModel().getSelectedItem().getYear().toString());
		} else if(editButton.getText().toString().equals("Save or Cancel")) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Edit SONG");
			alert.setHeaderText("Confirmation");
			alert.setContentText("Are you sure you want to edit this song?");
			Optional<ButtonType> btn = alert.showAndWait();
			if(btn.get() == ButtonType.CANCEL) {
				
			} else if(btn.get() == ButtonType.OK) {
				int count = 0;
				int i = 0;
				for(i=0;i<obsList.size();i++) {
					if((editSong.getText().toString().equalsIgnoreCase(tableView.getItems().get(i).getSong().toString()) && editArtist.getText().toString().equalsIgnoreCase(tableView.getItems().get(i).getArtist().toString())) && i != tableView.getSelectionModel().getSelectedIndex()) {
						Alert error = new Alert(AlertType.ERROR);
						error.setTitle("Error");
						error.setHeaderText("Error on edit");
						error.setContentText("This song and artist is already in the library");
						error.showAndWait();
						count = 1;
						break;
					}
				}
				if(count == 0 && !editSong.getText().toString().equals("") && !editArtist.getText().toString().equals("")) {
					if(songDisplay.isVisible() == false) {
						songDisplay.setVisible(true);
						albumDisplay.setVisible(true);
						yearDisplay.setVisible(true);
						artistDisplay.setVisible(true);
					}
					tableView.getSelectionModel().getSelectedItem().setSong(editSong.getText().toString());
					tableView.getSelectionModel().getSelectedItem().setArtist(editArtist.getText().toString());
					tableView.getSelectionModel().getSelectedItem().setAlbum(editAlbum.getText().toString());
					tableView.getSelectionModel().getSelectedItem().setYear(editYear.getText().toString());
					tableView.refresh();
					compareSort();
					write();
					for(i=0;i<obsList.size();i++) {
						if(editSong.getText().toString().equalsIgnoreCase(tableView.getItems().get(i).getSong().toString()) && editArtist.getText().toString().equalsIgnoreCase(tableView.getItems().get(i).getArtist().toString())) {
							tableView.getSelectionModel().select(i);
							display(i);
							break;
						} 
					}
				} else if (editSong.getText().toString().equals("") || editArtist.getText().toString().equals("")) {
					Alert error = new Alert(AlertType.ERROR);
					error.setTitle("Error");
					error.setHeaderText("Missing Information");
					error.setContentText("Song and/or Artist is empty");
					error.showAndWait();
				}
			}
			editSong.setVisible(false);
			editArtist.setVisible(false);
			editAlbum.setVisible(false);
			editYear.setVisible(false);
			editButton.setText("Edit Song");
			deleteButton.setDisable(false);
			addButton.setDisable(false);
			tableView.setDisable(false);
		}
	}
	
	@FXML
	private void deleteClicked(ActionEvent event) throws IOException {
		int selected = tableView.getSelectionModel().getSelectedIndex();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("ADD SONG");
		alert.setContentText("Confirmation");
		alert.setContentText("Are you sure you want to add this song to the library?");
		Optional<ButtonType> btn = alert.showAndWait();
		if(btn.get() == ButtonType.CANCEL) {
			
		} else if(btn.get() == ButtonType.OK) {
			tableView.getItems().remove(selected);
			write();
		}
		checkTable(selected);
	}
	
	public List<Song> read(String uri) throws Exception{
		
		BufferedReader br;
		try{
		br = new BufferedReader(new FileReader(uri));
		}
		catch(FileNotFoundException err){	
			File file = new File(uri);
			  if(!file.exists()){
			     file.getParentFile().mkdirs();
			     file.createNewFile();
			  }
			
			br = new BufferedReader(new FileReader(uri));		
		}
	    String line = null;
	    
	    List<Song> songList = new ArrayList<Song>();
	    while ((line = br.readLine()) != null) {
	    	if(line != ""){
	      String[] values = line.split(",");
	      if(values.length == 2){
		      songList.add(new Song(values[0],values[1],"",""));}
	      if(values.length == 3){
		      songList.add(new Song(values[0],values[1],values[2],""));
	      }
	      if(values.length == 4){
		      songList.add(new Song(values[0],values[1],values[2],values[3]));}
	      }else{
	    	  return null;
	      }
	    }
	    br.close();

	    
	    return songList;
		
	}
	
	public void write(){
		File fnew = new File(System.getProperty("user.dir") + "src/songlib.txt");
		String source = "";
		
		boolean first = true;
		for (Song song : obsList) {
			if(first){
				source = song.getSong() + "," + song.getArtist() + "," + song.getAlbum() + "," + song.getYear();	 
        	}else{
        		source = source + "\n" + song.getSong() + "," + song.getArtist() + "," + song.getAlbum() + "," + song.getYear();	 
        	}
			first = false;
        }
		
		try {
		    FileWriter filewriter = new FileWriter(fnew, false);
		    filewriter.write(source);
		    filewriter.close();
			}
			catch (IOException e) {
			    e.printStackTrace();
			} 
		
		}
	
	public void start(Stage mainStage) throws Exception {
		
		obsList = FXCollections.observableArrayList(read(System.getProperty("user.dir") + "src/songlib.txt"));
		
		addArtist.setVisible(false);
		addSong.setVisible(false);
		addAlbum.setVisible(false);
		addYear.setVisible(false);
		
		editArtist.setVisible(false);
		editSong.setVisible(false);
		editAlbum.setVisible(false);
		editYear.setVisible(false);
		
		song.setCellValueFactory(new PropertyValueFactory<Song, String>("song"));
		
		artist.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
		
		compareSort();
		
		tableView.setItems(obsList);
		
		if(obsList.size() < 1) {
			checkTable(0);
		} else {
			display(0);
		}
	    
	 }

}
