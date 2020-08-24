package dev.nest.krisp.objects;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.utils.Utils;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.net.MalformedURLException;

public class DataStorage {

    private boolean isVerifEnabled, isRulesEnabled;

    private final String guildId;
    private String prefix;
    private String restrictedChannelId;
    private String minimumRoleId;
    private String verif_channelId;
    private String verif_reactionUnicode;
    private String verif_roleId, verif_roleToBeRemoved;
    private String verif_messageId;
    private String verif_imageURL;
    private String verif_message;
    private String rules_channelId;
    private String rules_reactionUnicode;
    private String rules_roleId, rules_roleToBeRemoved;
    private String rules_messageId;
    private String rules_imageURL;
    private String rules_message;

    public DataStorage(String prefix, String guildId) {
        this.guildId = guildId;
        this.prefix = prefix;
        isVerifEnabled = false;
        restrictedChannelId = null;
        minimumRoleId = null;
        verif_channelId = null;
        verif_reactionUnicode = null;
        verif_roleId = null;
        verif_roleToBeRemoved = null;
        verif_messageId = null;
        verif_imageURL = null;
        verif_message = null;
        isRulesEnabled = false;
        rules_channelId = null;
        rules_reactionUnicode = null;
        rules_roleId = null;
        rules_roleToBeRemoved = null;
        rules_messageId = null;
        rules_imageURL = null;
        rules_message = null;
        Krisp.getDataManager().putData(guildId, this);
    }

    public TextChannel getRestrictedChannel() {
        if (restrictedChannelId!= null) {
            if (!restrictedChannelId.equalsIgnoreCase("null")) {
                return Krisp.getJDA().getTextChannelById(restrictedChannelId);
            }
        }
        return null;
    }

    public Role getMinimumRole() {
        if (minimumRoleId!= null) {
            if (!minimumRoleId.equalsIgnoreCase("null")) {
                return Krisp.getJDA().getRoleById(minimumRoleId);
            }
        }
        return null;
    }

    public boolean hasMinimumRole() {
        return minimumRoleId != null && !minimumRoleId.equalsIgnoreCase("null");
    }

    public boolean hasRestrictedChannel() {
        return restrictedChannelId != null && !restrictedChannelId.equalsIgnoreCase("null");
    }

    public void setRestrictedChannelId(String restrictedChannelId) {
        this.restrictedChannelId = restrictedChannelId;
        Krisp.getHandler().saveData(guildId);
    }

    public void setMinimumRoleId(String minimumRoleId) {
        this.minimumRoleId = minimumRoleId;
        Krisp.getHandler().saveData(guildId);
    }

    public String getRestrictedChannelId() {
        return restrictedChannelId;
    }

    public String getMinimumRoleId() {
        return minimumRoleId;
    }

    public String getVerif_roleToBeRemoved() {
        return verif_roleToBeRemoved;
    }

    public String getRules_roleToBeRemoved() {
        return rules_roleToBeRemoved;

    }

    public Role getVerifRoleToBeRemoved() {
        if (verif_roleToBeRemoved != null) {
            if (!verif_roleToBeRemoved.equalsIgnoreCase("null")) {
                return Krisp.getJDA().getRoleById(verif_roleToBeRemoved);
            }
        }
        return null;
    }

    public Role getRulesRoleToBeRemoved() {
        if (rules_roleToBeRemoved != null) {
            if (!rules_roleToBeRemoved.equalsIgnoreCase("null")) {
                return Krisp.getJDA().getRoleById(rules_roleToBeRemoved);
            }
        }
        return null;
    }

    public void setVerif_roleToBeRemoved(String verif_roleToBeRemoved) {
        this.verif_roleToBeRemoved = verif_roleToBeRemoved;
        Krisp.getHandler().saveData(guildId);
    }

    public void setRules_roleToBeRemoved(String rules_roleToBeRemoved) {
        this.rules_roleToBeRemoved = rules_roleToBeRemoved;
        Krisp.getHandler().saveData(guildId);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        Krisp.getHandler().saveData(guildId);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setVerifEnabled(boolean isVerifEnabled) {
        this.isVerifEnabled = isVerifEnabled;
        Krisp.getHandler().saveData(guildId);
    }

    public void setVerif_channelId(String verif_channelId) {
        this.verif_channelId = verif_channelId;
        Krisp.getHandler().saveData(guildId);
    }

    public void setVerif_reactionUnicode(String verif_reactionUnicode) {
        this.verif_reactionUnicode = verif_reactionUnicode;
        Krisp.getHandler().saveData(guildId);
    }

    public void setVerif_roleId(String verif_roleId) {
        this.verif_roleId = verif_roleId;
        Krisp.getHandler().saveData(guildId);
    }

    public void setVerif_messageId(String verif_messageId) {
        this.verif_messageId = verif_messageId;
        Krisp.getHandler().saveData(guildId);
    }

    public void setVerif_imageURL(String verif_imageURL) throws MalformedURLException {
        if (Utils.isURL(verif_imageURL)) {
            this.verif_imageURL = verif_imageURL;
            Krisp.getHandler().saveData(guildId);
        } else {
            throw new MalformedURLException("URL provided is invalid!");
        }
    }

    public void setVerif_message(String verif_message) {
        this.verif_message = verif_message;
        Krisp.getHandler().saveData(guildId);
    }

    public boolean getVerifEnabled() {
        return isVerifEnabled;
    }

    public String getVerif_channelId() {
        return verif_channelId;
    }

    public String getVerif_reactionUnicode() {
        return verif_reactionUnicode;
    }

    public String getVerif_roleId() {
        return verif_roleId;
    }

    public String getVerif_messageId() {
        return verif_messageId;
    }

    public String getVerif_imageURL() {
        return verif_imageURL;
    }

    public String getVerif_message() {
        return verif_message;
    }

    public void setRulesEnabled(boolean isRulesEnabled) {
        this.isRulesEnabled = isRulesEnabled;
        Krisp.getHandler().saveData(guildId);
    }

    public void setRules_channelId(String rules_channelId) {
        this.rules_channelId = rules_channelId;
        Krisp.getHandler().saveData(guildId);
    }

    public void setRules_reactionUnicode(String rules_reactionUnicode) {
        this.rules_reactionUnicode = rules_reactionUnicode;
        Krisp.getHandler().saveData(guildId);
    }

    public void setRules_roleId(String rules_roleId) {
        this.rules_roleId = rules_roleId;
        Krisp.getHandler().saveData(guildId);
    }

    public void setRules_messageId(String rules_messageId) {
        this.rules_messageId = rules_messageId;
        Krisp.getHandler().saveData(guildId);
    }

    public boolean hasVerifImage() {
        if (verif_imageURL != null) {
            return !verif_imageURL.equalsIgnoreCase("null");
        }
        return false;
    }

    public boolean hasRulesImage() {
        if (rules_imageURL != null) {
            return !rules_imageURL.equalsIgnoreCase("null");
        }
        return false;
    }

    public boolean hasVerifMessage() {
        if (verif_messageId != null) {
            return !verif_messageId.equalsIgnoreCase("null");
        }
        return false;
    }

    public boolean hasRulesMessage() {
        if (rules_messageId != null) {
            return !rules_messageId.equalsIgnoreCase("null");
        }
        return false;
    }


    public void setRules_imageURL(String rules_imageURL) throws MalformedURLException {
        if (Utils.isURL(rules_imageURL)) {
            this.rules_imageURL = rules_imageURL;
            Krisp.getHandler().saveData(guildId);
        } else {
            throw new MalformedURLException("URL provided is invalid!");
        }
    }

    public void setRules_message(String rules_message) {
        this.rules_message = rules_message;
        Krisp.getHandler().saveData(guildId);
    }

    public boolean hasVerifRemovableRole() {
        return verif_roleToBeRemoved != null && !verif_roleToBeRemoved.equalsIgnoreCase("null");
    }

    public boolean hasRulesRemovableRole() {
        return rules_roleToBeRemoved != null && !rules_roleToBeRemoved.equalsIgnoreCase("null");
    }

    public boolean getRulesEnabled() {
        return isRulesEnabled;
    }

    public String getRules_channelId() {
        return rules_channelId;
    }

    public String getRules_reactionUnicode() {
        return rules_reactionUnicode;
    }

    public String getRules_roleId() {
        return rules_roleId;
    }

    public String getRules_messageId() {
        return rules_messageId;
    }

    public String getRules_imageURL() {
        return rules_imageURL;
    }

    public String getRules_message() {
        return rules_message;
    }

    public TextChannel getVerifChannel() {
        if (verif_channelId != null) {
            if (!verif_channelId.equalsIgnoreCase("null")) {
                return Krisp.getJDA().getTextChannelById(verif_channelId);
            }
        }
        return null;
    }

    public TextChannel getRulesChannel() {
        if (rules_channelId != null) {
            if (!rules_channelId.equalsIgnoreCase("null")) {
                return Krisp.getJDA().getTextChannelById(rules_channelId);
            }
        }
        return null;
    }

    public Role getVerifRole() {
        if (verif_roleId != null) {
            if (!verif_roleId.equalsIgnoreCase("null")) {
                return Krisp.getJDA().getRoleById(verif_roleId);
            }
        }
        return null;
    }

    public Role getRulesRole() {
        if (rules_roleId != null) {
            if (!rules_roleId.equalsIgnoreCase("null")) {
                return Krisp.getJDA().getRoleById(rules_roleId);
            }
        }
        return null;
    }

    public boolean isVerifComplete() {
        return getVerifChannel() != null && getVerifRole() != null && verif_message != null && verif_reactionUnicode != null;
    }

    public boolean isRulesComplete() {
        return getRulesChannel() != null && getRulesRole() != null && rules_message != null && rules_reactionUnicode != null;
    }

    @Override
    public String toString() {
        return prefix +
                " " +
                restrictedChannelId +
                " " +
                minimumRoleId +
                " " +
                isVerifEnabled +
                " " +
                verif_channelId +
                " " +
                verif_reactionUnicode +
                " " +
                verif_roleId +
                " " +
                verif_roleToBeRemoved +
                " " +
                verif_messageId +
                " " +
                verif_imageURL +
                " " +
                isRulesEnabled +
                " " +
                rules_channelId +
                " " +
                rules_reactionUnicode +
                " " +
                rules_roleId +
                " " +
                rules_roleToBeRemoved +
                " " +
                rules_messageId +
                " " +
                rules_imageURL;
    }

}
