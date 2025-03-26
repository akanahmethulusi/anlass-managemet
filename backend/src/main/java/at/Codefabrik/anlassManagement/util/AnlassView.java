package at.Codefabrik.anlassManagement.util;

import at.Codefabrik.anlassManagement.entity.Anlass;
import at.Codefabrik.anlassManagement.repository.AnlassRepository;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileData;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.component.textarea.TextArea;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.RouterLink;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

@Route("anlaesse")
public class AnlassView extends VerticalLayout {
    private final AnlassRepository anlassRepository;
    private final Grid<Anlass> grid = new Grid<>(Anlass.class);
    private final TextField searchField = new TextField("Suche nach Title");
    private final Button saveButton = new Button("Speichern");
    private final Button deleteButton = new Button("Löschen");
    private final Button searchButton = new Button("Search");
    private final TextField titleField = new TextField("Titel");
    private final TextArea beschreibungField = new TextArea("Beschreibung");
    private final NumberField maxTeilnehmerField = new NumberField("Max Teilnehmer");
    private final MemoryBuffer buffer = new MemoryBuffer();
    private final Upload fileUpload = new Upload(buffer);
    private final Button downloadButton = new Button("Datei herunterladen");
    private final Span fileInfo = new Span();

    public AnlassView(AnlassRepository anlassRepository) {
        this.anlassRepository = anlassRepository;
        grid.setItems(anlassRepository.findAll());
        grid.setColumns("id", "title", "beschreibung", "maxTeilnehmer");
        grid.asSingleSelect().addValueChangeListener(event->{
            Anlass selected = event.getValue();
            if(selected != null){
                getUI().ifPresent(ui -> ui.navigate(AnlassDetailView.class, selected.getId()));
            }
        });

        saveButton.addClickListener(e -> {
            Anlass anlass = new Anlass(titleField.getValue(), beschreibungField.getValue(), maxTeilnehmerField.getValue().intValue(), null, null, null);
            anlassRepository.save(anlass);
            grid.setItems(anlassRepository.findAll());
            Notification.show("Anlass gespeichert");
        });

        deleteButton.addClickListener(e->{
           Anlass selected = grid.asSingleSelect().getValue();
           if(selected != null){
               anlassRepository.deleteById(selected.getId());
               grid.setItems(anlassRepository.findAll());
               Notification.show("Anlass gelöscht");
               fileInfo.setText("");
           }
        });

        searchButton.addClickListener(e->{
           String searchQuery = searchField.getValue();
           if (!searchQuery.isEmpty()){
               grid.setItems(anlassRepository.findByTitleContainingIgnoreCase(searchQuery));
           }else {
               grid.setItems(anlassRepository.findAll());
           }
        });

       fileUpload.addSucceededListener(event->{
          Anlass selected = grid.asSingleSelect().getValue();
          if(selected != null){
              try {
                  byte[] fileData = buffer.getInputStream().readAllBytes();
                  selected.setFileData(fileData);
                  anlassRepository.save(selected);
                  Notification.show("Datei erfolgreich hoch geladen");
              } catch (IOException ex) {
                  Notification.show("Fehler beim hochladen der Datei");
              }
          }else{
              Notification.show("Bitte wähle zuerst einen Anlass aus");
          }
       });

       downloadButton.addClickListener(e->{
           Anlass selected = grid.asSingleSelect().getValue();
           if(selected != null && selected.getFileData() != null){
               getUI().ifPresent(ui->ui.getPage().open("/api/anlaesse/download/" + selected.getId()));
           }else {
               Notification.show("Keine Datei zum Herunterladen vorhanden");
           }
       });

        Tabs menu = new Tabs(
                new Tab(new RouterLink("Info", AnlassView.class)),
                new Tab(new RouterLink("Fragen", AnlassView.class)),
                new Tab(new RouterLink("Kategorien", AnlassView.class)),
                new Tab(new RouterLink("Gruppen", AnlassView.class)),
                new Tab(new RouterLink("Anmeldungen", AnlassView.class))
        );

        add(menu, searchField, searchButton, titleField, beschreibungField, maxTeilnehmerField, fileUpload, fileInfo, saveButton, deleteButton, downloadButton, grid);

    }

    public void updateFileInfo(Anlass anlass){
        if(anlass.getFileName() != null){
            fileInfo.setText("Hochgeladene Datei: " + anlass.getFileName() + " (" + anlass.getFileType() + ")");
        }else{
            fileInfo.setText("Keine Datei hochgeladen");
        }
    }
}
