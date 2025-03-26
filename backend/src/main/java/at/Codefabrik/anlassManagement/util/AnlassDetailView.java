package at.Codefabrik.anlassManagement.util;

import at.Codefabrik.anlassManagement.entity.Anlass;
import at.Codefabrik.anlassManagement.repository.AnlassRepository;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;

import java.awt.*;
import java.util.Optional;


@Route("/anlaesse/:id")
@PageTitle("Anlass Details")
public class AnlassDetailView extends VerticalLayout implements HasUrlParameter<Long>{
    private final AnlassRepository anlassRepository;
    private final TextField textField = new TextField("Titel");
    private final TextArea beschreibungField = new TextArea("Beschreibung");
    private final NumberField maxTeilnehmerField = new NumberField("Max. Teilnehmer");
    private final Button saveButton = new Button("Änderungen speichern");

    private Long anlassId;

    public AnlassDetailView(AnlassRepository anlassRepository) {
        this.anlassRepository = anlassRepository;
    }
    @Override
    public void setParameter(BeforeEvent event, Long id){
        this.anlassId = id;
        Optional<Anlass> anlass = anlassRepository.findById(id);
        if(anlass.isPresent()){
            textField.setValue(anlass.get().getTitle());
            beschreibungField.setValue(anlass.get().getBeschreibung());


            saveButton.addClickListener(e->{
                anlass.get().setTitle(textField.getValue());
                anlass.get().setBeschreibung(beschreibungField.getValue());
                anlassRepository.save(anlass.get());
                Notification.show("Anlass aktualisiert!");
            });

            Button backButton = new Button("Zurück", e-> getUI().ifPresent(ui -> ui.navigate(AnlassView.class)));
            backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            add(textField, beschreibungField, saveButton, backButton);
        }else{
            add(new Button("Zurück", e->getUI().ifPresent(ui->ui.navigate(AnlassView.class))));
            Notification.show("Anlass nicht gefunden");
        }
    }
}
