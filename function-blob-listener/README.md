# Emulating Blob Storage with Azurite and Downloading Storage Explorer

## Prerequisites

- [Node.js](https://nodejs.org/) installed on your machine.
- [Azurite - Local Azure Storage emulator](https://learn.microsoft.com/en-us/azure/storage/common/storage-use-azurite?tabs=npm%2Cblob-storage#install-azurite) installed (see below)
- [Azure Storage Explorer](https://azure.microsoft.com/en-us/products/storage/storage-explorer/) downloaded and installed.
- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) installed on your machine.
- [Gradle](https://gradle.org/install/) installed on your machine.

## Setting Up Azurite

1. **Install Azurite**:
    ```sh
    npm install -g azurite
    ```

1. **Run Azurite**:
    ```sh
    azurite --silent --location ./ --debug ./azurite-debug.log
    ```

1. **Verify Azurite is Running**:
    Azurite should now be running on `http://127.0.0.1:10000` for Blob service.

## Configuring Azure Storage Explorer

1. **Open Azure Storage Explorer**.

1. **Add an Account**:
    - Click on `Add an Account`.
    - Select `Attach to a local emulator`.
    - Choose `http://127.0.0.1:10000` for the Blob service.

1. **Verify Connection**:
    - You should see the local emulator connected in the explorer pane.
    - You can now create container *drools-workitems* and upload blobs to your local Azurite instance.


## Conclusion

You have successfully set up Azurite to emulate Azure Blob Storage and configured Azure Storage Explorer to interact with it. This setup allows you to develop and test your application locally without needing an actual Azure account.

