package dev.nest.krisp.objects;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.utils.Utils;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class RulesData {

    private boolean isEnabled;
    private String message;
    private String reactionUnicode;
    private String imageURL;
    private String messageId;
    private String roleId;
    private String channelId;
    private final String guildId;

    public RulesData(String guildId) {
        this.guildId = guildId;
        isEnabled = false;
        channelId = null;
        message = null;
        reactionUnicode = null;
        roleId = null;
        imageURL = null;
        messageId = null;
        Krisp.getRuleDataManager().putData(this.guildId, this);
    }

    public boolean hasImage() {
        if (imageURL != null) {
            return !imageURL.equalsIgnoreCase("null") && Utils.isURL(imageURL);
        }
        return false;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReactionUnicode(String reactionUnicode) {
        this.reactionUnicode = reactionUnicode;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public TextChannel getChannel() {
        if (channelId != null) {
            if (!channelId.equalsIgnoreCase("null")) {
                return Krisp.getJDA().getTextChannelById(channelId);
            }
        }
        return null;
    }

    public String getMessage() {
        return message;
    }

    public String getReactionUnicode() {
        return reactionUnicode;
    }

    public Role getRole() {
        if (roleId != null) {
            if (!roleId.equalsIgnoreCase("null")) {
                return Krisp.getJDA().getRoleById(roleId);
            }
        }
        return null;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public boolean isComplete() {
        return getChannel() != null && getRole() != null && message != null && reactionUnicode != null;
    }

    public boolean hasMessage() {
        if (messageId != null) {
            return !messageId.equalsIgnoreCase("null");
        }
        return false;
    }

    @Override
    public String toString() {
        String channelplace, roleplace;
        if (getChannel() == null) {
            channelplace = "null";
        } else {
            channelplace = getChannel().getId();
        }
        if (getRole() == null) {
            roleplace = "null";
        } else {
            roleplace = getRole().getId();
        }
        return isEnabled
                + " "
                + channelplace
                + " "
                + reactionUnicode
                + " "
                + roleplace
                + " "
                + messageId
                + "\n"
                + imageURL
                + "\n"
                + message.replaceAll(" ", "#");
    }

}
