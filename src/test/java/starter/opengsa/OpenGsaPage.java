package starter.opengsa;

import net.serenitybdd.core.pages.PageComponent;
import net.serenitybdd.core.pages.WebElementFacade;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sun.xml.bind.v2.WellKnownNamespace;

public class OpenGsaPage extends PageComponent {

    @FindBy(id="anch_75")
    private WebElementFacade downloadLink;

    public String getHref() {
        return downloadLink.getAttribute("href");
    }
    
    public String getLinkText() {
        return downloadLink.getText();
    }
    
    public WebElement getDownloadLink() {
    	return downloadLink.getElement();
    }
}