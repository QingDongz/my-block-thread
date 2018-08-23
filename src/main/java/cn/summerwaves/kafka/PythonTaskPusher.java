package cn.summerwaves.kafka;


public interface PythonTaskPusher {
    public boolean pushTask(Integer command, String bankName, String account,
                            String password, Integer bankId, Integer sessionTimeout,
                            Integer bankCardId);

    public void setTopic(String topic);
}
