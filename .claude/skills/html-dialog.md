# HTML Dialog Development (Axon Ivy 13.2)

## Structure
- .xhtml file: View (JSF/Facelets)
- .ivyScript file: Logic
- Data class: Model

## PrimeFaces Components
```xhtml
<!-- Data Table -->
<p:dataTable value="#{data.customers}" var="customer">
    <p:column headerText="Name">
        <h:outputText value="#{customer.name}" />
    </p:column>
</p:dataTable>

<!-- Input with validation -->
<p:inputText value="#{data.email}" required="true">
    <f:validateRegex pattern="^[\\w.-]+@[\\w.-]+\\.\\w+$" />
</p:inputText>

<!-- AJAX update -->
<p:commandButton value="Save" action="#{logic.save}" 
    update="messages,dataTable" />
```

## Logic Class Pattern
```java
public class MyDialogLogic extends HtmlDialogLogic<MyDialogData> {
    
    public void save() {
        // Access data via getData()
        MyDialogData data = getData();
        
        // Call Ivy services
        Ivy.persistence().get("main").save(data.getCustomer());
        
        // Show message
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage("Saved successfully"));
    }
}
```

## Best Practices
- Use p:messages for feedback
- Implement p:blockUI for long operations
- Use p:autoComplete for large dropdowns
- Apply responsive grid: p:panelGrid with columns