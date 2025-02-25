package com.rhcsoft.spring.drools.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.BlobTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.StorageAccount;

/**
 * Azure Functions with Azure Blob trigger.
 */
public class DroolsBlobTrigger {
    /**
     * This function will be invoked when a new or updated blob is detected at the
     * specified path. The blob contents are provided as input to this function.
     */
    @FunctionName("BlobTrigger")
    @StorageAccount("AzureWebJobsStorage")
    public void run(
            @BlobTrigger(name = "content", path = "drools-workitems/{name}", dataType = "binary") byte[] content,
            @BindingName("name") String name,
            final ExecutionContext context) {
        context.getLogger().info("Java Blob trigger function processed a blob. Name: " + name + "\n  Size: "
                + content.length + " Bytes");
    }
}
