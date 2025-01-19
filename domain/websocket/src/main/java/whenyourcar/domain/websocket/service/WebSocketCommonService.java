package whenyourcar.domain.websocket.service;

public interface WebSocketCommonService {
    public void postMessageToRedisStream(Long roomId,
                                         Long userId,
                                         String message);
}
