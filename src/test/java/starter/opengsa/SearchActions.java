package starter.opengsa;

import net.serenitybdd.core.steps.UIInteractions;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class SearchActions extends UIInteractions {

    @Step("Search by keyword '{0}'")
    public void searchBy(String keyword) {
        $("#searchInput").sendKeys(keyword, Keys.ENTER);
    }
    
    @Step("Click download link")
    public WebElement getDownloadEelement() {
        return $("#anch_75").getElement();
    }
}
