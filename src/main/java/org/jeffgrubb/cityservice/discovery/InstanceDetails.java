package org.jeffgrubb.cityservice.discovery;

/**
 * Created by jgrubb on 10/18/16.
 */
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName("details")
public class InstanceDetails
{
    private String        description;

    public InstanceDetails()
    {
        this("");
    }

    public InstanceDetails(String description)
    {
        this.description = description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
