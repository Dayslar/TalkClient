package by.dayslar.sample.Model;

public class Report {

    private int count;
    private int answerCount;
    private int notAnswerCount;

    private int incomingCount;
    private int incomingAnswerCount;
    private int incomingNotAnswerCount;

    private int outgoingCount;
    private int outgoingAnswerCount;
    private int outgoingNotAnswerCount;

    private long duration;
    private long incomingDuration;
    private long outgoingDuration;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public int getNotAnswerCount() {
        return notAnswerCount;
    }

    public void setNotAnswerCount(int notAnswerCount) {
        this.notAnswerCount = notAnswerCount;
    }

    public int getIncomingCount() {
        return incomingCount;
    }

    public void setIncomingCount(int incomingCount) {
        this.incomingCount = incomingCount;
    }

    public int getIncomingAnswerCount() {
        return incomingAnswerCount;
    }

    public void setIncomingAnswerCount(int incomingAnswerCount) {
        this.incomingAnswerCount = incomingAnswerCount;
    }

    public int getIncomingNotAnswerCount() {
        return incomingNotAnswerCount;
    }

    public void setIncomingNotAnswerCount(int incomingNotAnswerCount) {
        this.incomingNotAnswerCount = incomingNotAnswerCount;
    }

    public int getOutgoingCount() {
        return outgoingCount;
    }

    public void setOutgoingCount(int outgoingCount) {
        this.outgoingCount = outgoingCount;
    }

    public int getOutgoingAnswerCount() {
        return outgoingAnswerCount;
    }

    public void setOutgoingAnswerCount(int outgoingAnswerCount) {
        this.outgoingAnswerCount = outgoingAnswerCount;
    }

    public int getOutgoingNotAnswerCount() {
        return outgoingNotAnswerCount;
    }

    public void setOutgoingNotAnswerCount(int outgoingNotAnswerCount) {
        this.outgoingNotAnswerCount = outgoingNotAnswerCount;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getIncomingDuration() {
        return incomingDuration;
    }

    public void setIncomingDuration(long incomingDuration) {
        this.incomingDuration = incomingDuration;
    }

    public long getOutgoingDuration() {
        return outgoingDuration;
    }

    public void setOutgoingDuration(long outgoingDuration) {
        this.outgoingDuration = outgoingDuration;
    }

    @Override
    public String toString() {
        return "count" + getCount() + "\n" +
                "answerCount" + getAnswerCount() + "\n" +
                "notAnswerCount" + getNotAnswerCount() + "\n" +
                "incomingCount" + getIncomingCount() + "\n" +
                "incomingAnswerCount" + getIncomingAnswerCount() + "\n" +
                "incomingNotAnswerCount" + getIncomingNotAnswerCount() + "\n" +
                "outgoingCount" + getOutgoingCount() + "\n" +
                "outgoingAnswerCount" + getOutgoingAnswerCount() + "\n" +
                "outgoingNotAnswerCount" + getOutgoingNotAnswerCount() + "\n" +
                "duration" + getDuration() + "\n" +
                "incomingDuration" + getIncomingDuration() + "\n" +
                "outgoingDuration" + getOutgoingDuration();

    }
}