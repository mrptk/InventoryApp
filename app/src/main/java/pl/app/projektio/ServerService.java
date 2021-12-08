package pl.app.projektio;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.JsonReader;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import pl.app.projektio.assets.*;

public class ServerService extends Service {
    private static final String RESTapiURL = "https://projio.azurewebsites.net/";
    private final IBinder binder = new ServiceBinder();
    private String username;
    private String password;
    private String token;
    private int userGroupID;
    private Exception exception;
    private boolean isError = false;
    private int response;
    public Item chosenItem;

    // pomocnicze do POST, PATCH, DELETE
    private int id;
    private int buildingId;
    private int floorId;
    private int roomId;
    private int statusInv;
    private String name;
    private String status;
    private String code;
    private String category;
    private String buyDate;
    private String startDate;
    private byte[] report;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class ServiceBinder extends Binder {
        ServerService getService() {
            return ServerService.this;
        }
    }

    public String getToken() { return token; }

    public int getUserGroupID() { return userGroupID; }

    public int getResponse() { return response; }

    public boolean isError() { return isError; }

    public String getExceptionMsg() { return exception.getMessage(); }

    public void createBuilding(Building building) {
        id = building.getId();
        name = building.getName();
        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Building");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept","application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("id", id);
                jsonInput.put("name", name);

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                os.write(jsonInput.toString());
                os.flush();
                os.close();
                out.close();

                response = connection.getResponseCode();
                connection.disconnect();
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });
        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void updateBuilding(Building building) {
        id = building.getId();
        name = building.getName();
        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Building");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("PATCH");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept","application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("id", id);
                jsonInput.put("name", name);

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                os.write(jsonInput.toString());
                os.flush();
                os.close();
                out.close();

                response = connection.getResponseCode();
                connection.disconnect();
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });
        t.start();
        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void deleteBuilding(Building building) {
        id = building.getId();
        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Building/" + id);
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("DELETE");
                connection.setRequestProperty("Authorization", "Bearer " + token);

                response = connection.getResponseCode();

                connection.disconnect();
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });
        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void createFloor(Floor floor) {
        id = floor.getId();
        buildingId = floor.getBuildingId();
        name = floor.getName();

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL (RESTapiURL + "Floor");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("id", id);
                jsonInput.put("buildingId", buildingId);
                jsonInput.put("name", name);

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                os.write(jsonInput.toString());
                os.flush();
                os.close();
                out.close();

                response = connection.getResponseCode();
                connection.disconnect();

            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });

        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void updateFloor(Floor floor) {
        id = floor.getId();
        buildingId = floor.getBuildingId();
        name = floor.getName();
        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Floor");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("PATCH");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept","application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("id", id);
                jsonInput.put("buildingId", buildingId);
                jsonInput.put("name", name);

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                os.write(jsonInput.toString());
                os.flush();
                os.close();
                out.close();

                response = connection.getResponseCode();
                connection.disconnect();
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });
        t.start();
        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void deleteFloor(Floor floor) {
        id = floor.getId();
        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Floor/" + id);
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("DELETE");
                connection.setRequestProperty("Authorization", "Bearer " + token);

                response = connection.getResponseCode();

                connection.disconnect();
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });
        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void createRoom(Room room) {
        id = room.getId();
        buildingId = room.getBuildingId();
        floorId = room.getFloorId();
        name = room.getName();

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL (RESTapiURL + "Room");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("id", id);
                jsonInput.put("buildingId", buildingId);
                jsonInput.put("floorId", floorId);
                jsonInput.put("name", name);

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                os.write(jsonInput.toString());
                os.flush();
                os.close();
                out.close();

                response = connection.getResponseCode();
                connection.disconnect();

            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });

        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void updateRoom(Room room) {
        id = room.getId();
        buildingId = room.getBuildingId();
        floorId = room.getFloorId();
        name = room.getName();
        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Room");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("PATCH");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept","application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("id", id);
                jsonInput.put("buildingId", buildingId);
                jsonInput.put("floorId", floorId);
                jsonInput.put("name", name);

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                os.write(jsonInput.toString());
                os.flush();
                os.close();
                out.close();

                response = connection.getResponseCode();
                connection.disconnect();
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });
        t.start();
        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void deleteRoom(Room room) {
        id = room.getId();
        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Room/" + id);
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("DELETE");
                connection.setRequestProperty("Authorization", "Bearer " + token);

                response = connection.getResponseCode();

                connection.disconnect();
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });
        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void createItem(Item item) {
        id = item.getId();
        roomId = item.getRoomId();
        status = item.getStatus();
        code = item.getCode();
        category = item.getCategory();
        buyDate = item.getBuyDate();
        name = item.getName();

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL (RESTapiURL + "Item");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("id", id);
                jsonInput.put("roomId", roomId);
                jsonInput.put("status", status);
                jsonInput.put("code", code);
                jsonInput.put("category", category);
                jsonInput.put("buyDate", buyDate);
                jsonInput.put("name", name);

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                os.write(jsonInput.toString());
                os.flush();
                os.close();
                out.close();

                response = connection.getResponseCode();
                connection.disconnect();

            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });

        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void updateItem(Item item) {
        id = item.getId();
        roomId = item.getRoomId();
        status = item.getStatus();
        code = item.getCode();
        category = item.getCategory();
        buyDate = item.getBuyDate();
        name = item.getName();

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Item");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("PATCH");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept","application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("id", id);
                jsonInput.put("roomId", roomId);
                jsonInput.put("status", status);
                jsonInput.put("code", code);
                jsonInput.put("category", category);
                jsonInput.put("buyDate", buyDate);
                jsonInput.put("name", name);

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                os.write(jsonInput.toString());
                os.flush();
                os.close();
                out.close();

                response = connection.getResponseCode();
                connection.disconnect();
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });
        t.start();
        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void deleteItem(Item item) {
        id = item.getId();
        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Item/" + id);
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("DELETE");
                connection.setRequestProperty("Authorization", "Bearer " + token);

                response = connection.getResponseCode();

                connection.disconnect();
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });
        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void createInventory(Inventory inventory) {
        id = inventory.getId();
        floorId = inventory.getFloorId();
        startDate = inventory.getStartDate();
        statusInv = inventory.getStatus();

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL (RESTapiURL + "Inventory");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("id", id);
                jsonInput.put("floorId", floorId);
                jsonInput.put("startDate", startDate);
                jsonInput.put("status", statusInv);

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                os.write(jsonInput.toString());
                os.flush();
                os.close();
                out.close();

                response = connection.getResponseCode();
                connection.disconnect();

            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });

        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void updateInventory(Inventory inventory) {
        id = inventory.getId();
        floorId = inventory.getFloorId();
        startDate = inventory.getStartDate();
        statusInv = inventory.getStatus();

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Inventory");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("PATCH");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept","application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("id", id);
                jsonInput.put("floorId", floorId);
                jsonInput.put("startDate", startDate);
                jsonInput.put("status", statusInv);

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                os.write(jsonInput.toString());
                os.flush();
                os.close();
                out.close();

                response = connection.getResponseCode();
                connection.disconnect();
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });
        t.start();
        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public void createInventoryItem(int id, int inventoryId, int itemId) {

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL (RESTapiURL + "IventoryItem");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("id", id);
                jsonInput.put("inventoryId", inventoryId);
                jsonInput.put("itemId", itemId);

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                os.write(jsonInput.toString());
                os.flush();
                os.close();
                out.close();

                response = connection.getResponseCode();
                connection.disconnect();

            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });

        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }
    }

    public ArrayList<Building> getBuildings() {
        final ArrayList<Building> buildings = new ArrayList<>();

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Building");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setDoInput(true);

                response = connection.getResponseCode();

                if (response == 200) {
                    InputStream responseBody = connection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody,"UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);


                    Building building;

                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        building = new Building();
                        while (jsonReader.hasNext()) {
                            String name = jsonReader.nextName();
                            if (name.equals("id")) building.setId(jsonReader.nextInt());
                            else if (name.equals("name")) building.setName(jsonReader.nextString());
                            else jsonReader.skipValue();
                        }
                        jsonReader.endObject();
                        buildings.add(building);
                    }
                    jsonReader.endArray();
                }

            } catch (Exception ex) {
                isError = true;
                exception = ex;
            }
        });
        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            isError = true;
            exception = ex;
        }

        return buildings;
    }

    public ArrayList<Floor> getFloors() {
        final ArrayList<Floor> floors = new ArrayList<>();

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Floor");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setDoInput(true);

                response = connection.getResponseCode();

                if (response == 200) {
                    InputStream responseBody = connection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody,"UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    Floor floor;

                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        floor = new Floor();
                        while (jsonReader.hasNext()) {
                            String name = jsonReader.nextName();
                            if (name.equals("id")) floor.setId(jsonReader.nextInt());
                            else if (name.equals("buildingId")) floor.setBuildingId(jsonReader.nextInt());
                            else if (name.equals("name")) floor.setName(jsonReader.nextString());
                            else jsonReader.skipValue();
                        }
                        jsonReader.endObject();
                        floors.add(floor);
                    }
                    jsonReader.endArray();
                }
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });

        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }

        return floors;
    }

    public ArrayList<Room> getRooms() {
        final ArrayList<Room> rooms = new ArrayList<>();

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Room");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setDoInput(true);

                response = connection.getResponseCode();

                if (response == 200) {
                    InputStream responseBody = connection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody,"UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    Room room;

                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        room = new Room();
                        while (jsonReader.hasNext()) {
                            String name = jsonReader.nextName();
                            if (name.equals("id")) room.setId(jsonReader.nextInt());
                            else if (name.equals("buildingId")) room.setBuildingId(jsonReader.nextInt());
                            else if (name.equals("floorId")) room.setFloorId(jsonReader.nextInt());
                            else if (name.equals("name")) room.setName(jsonReader.nextString());
                            else jsonReader.skipValue();
                        }
                        jsonReader.endObject();
                        rooms.add(room);
                    }
                    jsonReader.endArray();
                }
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });

        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }

        return rooms;
    }

    public ArrayList<Item> getItems() {
        final ArrayList<Item> items = new ArrayList<>();

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Item");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setDoInput(true);

                response = connection.getResponseCode();

                if (response == 200) {
                    InputStream responseBody = connection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody,"UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    Item item;

                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        item = new Item();
                        while (jsonReader.hasNext()) {
                            String name = jsonReader.nextName();
                            if (name.equals("id")) item.setId(jsonReader.nextInt());
                            else if (name.equals("roomId")) item.setRoomId(jsonReader.nextInt());
                            else if (name.equals("status")) item.setStatus(jsonReader.nextString());
                            else if (name.equals("code")) item.setCode(jsonReader.nextString());
                            else if (name.equals("category")) item.setCategory(jsonReader.nextString());
                            else if (name.equals("buyDate")) item.setBuyDate(jsonReader.nextString());
                            else if (name.equals("name")) item.setName(jsonReader.nextString());
                            else jsonReader.skipValue();
                        }
                        jsonReader.endObject();
                        items.add(item);
                    }
                    jsonReader.endArray();
                }
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });

        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }

        return items;
    }

    public ArrayList<Inventory> getInventories() {
        final ArrayList<Inventory> inventories = new ArrayList<>();

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Inventory");
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setDoInput(true);

                response = connection.getResponseCode();

                if (response == 200) {
                    InputStream responseBody = connection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody,"UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    Inventory inventory;

                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        inventory = new Inventory();
                        while (jsonReader.hasNext()) {
                            String name = jsonReader.nextName();
                            if (name.equals("id")) inventory.setId(jsonReader.nextInt());
                            else if (name.equals("floorId")) inventory.setFloorId(jsonReader.nextInt());
                            else if (name.equals("startDate")) inventory.setStartDate(jsonReader.nextString());
                            else if (name.equals("status")) inventory.setStatus(jsonReader.nextInt());
                            else jsonReader.skipValue();
                        }
                        jsonReader.endObject();
                        inventories.add(inventory);
                    }
                    jsonReader.endArray();
                }
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });

        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }

        return inventories;
    }

    public ArrayList<Item> getInventoryItems(Inventory inventory) {
        final ArrayList<Item> inventoryItems = new ArrayList<>();
        final ItemList allItems = new ItemList(getItems());

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "IventoryItem/" + inventory.getId());
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setDoInput(true);

                response = connection.getResponseCode();

                if (response == 200) {
                    InputStream responseBody = connection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody,"UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    Item item = null;

                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String name = jsonReader.nextName();
                            if (name.equals("itemId")) item = allItems.getItemById(jsonReader.nextInt());
                            else jsonReader.skipValue();
                        }
                        jsonReader.endObject();
                        inventoryItems.add(item);
                    }
                    jsonReader.endArray();
                }
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });

        t.start();

        try {
            t.join();
        } catch (Exception ex) {
            exception = ex;
            isError = true;
        }

        return inventoryItems;
    }

    public void getReport(Inventory inventory) {
        String inventoryId = "" + inventory.getId();
        String iStartDate = inventory.getStartDate();
        String pathToDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "Raport_" + iStartDate.substring(0,iStartDate.indexOf("T")) + ".pdf";
        File pdfFile = new File(pathToDirectory, fileName);

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Report/" + inventoryId);
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.connect();

                response = connection.getResponseCode();

                if (response == 200) {
                    //Michal - 2021.06.28 - Report download fix
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader responseReader = new InputStreamReader(inputStream, "UTF-8");

                    //Read stream as string
                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                    String baseEncodedString = r.readLine();
                    baseEncodedString = baseEncodedString.replace("\"", ""); //Delete " from string

                    //Decode string to byte array
                    byte[] decodedArray = Base64.getDecoder().decode(baseEncodedString);

                    //Save array to file
                    FileOutputStream os = new FileOutputStream(pdfFile);
                    os.write(decodedArray);

                    inputStream.close();
                    responseReader.close();
                    os.close();
                    //Michal - 2021.06.28 - Report download fix
                }
                connection.disconnect();
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });

        t.start();
    }
    /*public void getReport(Inventory inventory) {
        String inventoryId = "" + inventory.getId();
        String iStartDate = inventory.getStartDate();
        String pathToDirectory = Environment.getExternalStorageDirectory().toString();
        String fileName = "Raport_" + iStartDate.substring(0,iStartDate.indexOf("T")) + ".pdf";
        File pdfFile = new File(pathToDirectory, fileName);

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Report/" + inventoryId);
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.connect();

                response = connection.getResponseCode();

                if (response == 200) {
                    InputStream inputStream = connection.getInputStream();
                    FileOutputStream os = new FileOutputStream(pdfFile);

                    byte[] buffer = new byte[1024 * 1024];
                    int bufferLength;

                    byte[] finalBytes;

                    while((bufferLength = inputStream.read(buffer)) > 0)
                        os.write(buffer, 0, bufferLength);

                    inputStream.close();
                    os.flush();
                    os.close();
                }
                connection.disconnect();
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });

        t.start();
    }*/

    public void logIn(String user, String pass) {
        username = user;
        password = pass;

        Thread t = new Thread(() -> {
            try {
                URL restApiUrl = new URL(RESTapiURL + "Auth"); // Laczymy sie z RESTem
                HttpsURLConnection connection = (HttpsURLConnection) restApiUrl.openConnection();

                connection.setRequestMethod("POST"); // Wysylamy requesta
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                JSONObject jsonInput = new JSONObject(); // Tworzymy jsona z nazwa uzytkownika i haslem
                jsonInput.put("username", username);
                jsonInput.put("password", password);

                DataOutputStream os = new DataOutputStream(connection.getOutputStream()); // Wysylamy jsona do resta
                os.writeBytes(jsonInput.toString());
                os.flush();
                os.close();

                response = connection.getResponseCode();

                if (response == 200) {
                    InputStream responseBody = connection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody,"UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    jsonReader.beginObject();
                    jsonReader.nextName();
                    token = jsonReader.nextString();
                    jsonReader.nextName();
                    userGroupID = jsonReader.nextInt();
                    jsonReader.close();
                }
                connection.disconnect();
            } catch (Exception ex) {
                exception = ex;
                isError = true;
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            exception = e;
            isError = true;
        }
    }

}


