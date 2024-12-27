package whenyourcar.mongo.service;


import whenyourcar.mongo.data.ChatRoom;


public interface MongoService {
    public void saveChatMessage(Long senderId, Long roomId, String message);

    public ChatRoom createOrFindRoom(Long user1Id, Long user2Id);
}
