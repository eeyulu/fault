package com.ht.fault.common.kit;

import io.rong.RongCloud;
import io.rong.messages.BaseMessage;
import io.rong.messages.ContactNtfMessage;
import io.rong.messages.ImgMessage;
import io.rong.messages.ImgTextMessage;
import io.rong.messages.ProfileNtfMessage;
import io.rong.messages.TxtMessage;
import io.rong.messages.VoiceMessage;
import io.rong.models.CheckOnlineResult;
import io.rong.models.CodeSuccessResult;
import io.rong.models.GroupInfo;
import io.rong.models.GroupUserQueryResult;
import io.rong.models.TokenResult;

import org.apache.log4j.Logger;

import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;

public class RongCloudKit {

	private static String appKey = PropKit.get("appKey");
	private static String appSecret = PropKit.get("appSecret");
	private static RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
	private Logger loger = Logger.getLogger(RongCloudKit.class);

	/**
	 * 刷新用户
	 */
	public int refresh(String portraitUri, String userId, String userName) {
		CodeSuccessResult userRefreshResult = null;
		// 刷新用户信息方法
		try {
			userRefreshResult = rongCloud.user.refresh(userId, userName, portraitUri);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userRefreshResult.getCode();
	}
	
	/**
	 * 检查用户在线状态 方法 
	 * @param userId 用户Id
	 * @return   0 未在线   1 在线
	 */
	public static String checkOnline(String userId){
		CheckOnlineResult checkOnline = null;
		try {
		 checkOnline = rongCloud.user.checkOnline(userId);
		 System.out.println(userId+":"+checkOnline);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		return checkOnline.getStatus();
	}
	
	/**
	 * 刷新群组信息RongCloud
	 */
	public Integer refreshGroupInfo(String groupId, String groupName) {
		// 刷新群组信息方法
		CodeSuccessResult groupRefreshResult = null;
		try {
			groupRefreshResult = rongCloud.group.refresh(groupId, groupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("refresh:  " + groupRefreshResult.toString());
		return groupRefreshResult.getCode();
	}
	
	/**
	 * 解散群方法RongCloud
	 * 
	 * @param userId
	 * @param groupId
	 */
	public Integer dismissGroupRong(String userId, String groupId) {
		CodeSuccessResult groupDismissResult = null;
		try {
			groupDismissResult = rongCloud.group.dismiss(userId, groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupDismissResult.getCode();
	}
	
	/**
	 * 退出群聊不再发送消息RongCloud
	 * 
	 * @param groupQuitUserId
	 */
	public Integer quitGroupRong(String[] groupQuitUserId, String groupId) {
		CodeSuccessResult groupQuitResult = null;
		try {
			groupQuitResult = rongCloud.group.quit(groupQuitUserId, groupId);
			System.out.println("quit:  " + groupQuitResult.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return groupQuitResult.getCode();
	}
	
	
	/**
	 * RongCloud查询群好友
	 * @param groupId
	 * @return
	 */
	public Integer queryGroupUser(String groupId) {
		GroupUserQueryResult groupQueryUserResult = null;
		// 查询群成员方法
		try {
			groupQueryUserResult = rongCloud.group.queryUser(groupId);
			System.out.println("queryUser:  " + groupQueryUserResult.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return groupQueryUserResult.getCode();
	}
	
	/**
	 * 添加组成员RongCloud
	 * 
	 * @param groupJoinUserId
	 * @param groupId
	 * @return
	 */
	public boolean joinGroup(String[] groupJoinUserId, String groupId, String groupName) {
		boolean result = false;
		// 将用户加入指定群组，用户将可以收到该群的消息，同一用户最多可加入 500 个群，每个群最大至 3000 人。
		CodeSuccessResult groupJoinResult = null;
		try {
			groupJoinResult = rongCloud.group.join(groupJoinUserId, groupId, groupName);
			System.out.println("join:  " + groupJoinResult.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (groupJoinResult.getCode().equals(200)) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 同步群组信息向融云发通知RongCloud
	 * 
	 * @param groupSyncGroupInfo
	 * @param userId
	 * @return
	 */
	public Integer syncGroupToRongCloud(GroupInfo[] groupSyncGroupInfo, String userId) {
		// 同步用户所属群组方法（当第一次连接融云服务器时，需要向融云服务器提交 userId
		// 对应的用户当前所加入的所有群组，此接口主要为防止应用中用户群信息同融云已知的用户所属群信息不同步。）
		CodeSuccessResult groupSyncResult = null;
		try {
			System.out.println("1:"+rongCloud);
			System.out.println("2:"+ rongCloud.group);
			System.out.println("userId:"+ userId);
			System.out.println("groupSyncGroupInfo:"+ groupSyncGroupInfo);
			groupSyncResult = rongCloud.group.sync(userId, groupSyncGroupInfo);
			System.out.println("3:"+JsonKit.toJson(groupSyncResult));
			return groupSyncResult.getCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 同步创建群组消息给融云
	 * @param groupCreateUserId
	 * @param groupId
	 * @param groupName
	 * @return
	 */
	public Integer createGroup(String[] groupCreateUserId,String groupId,String groupName){
		CodeSuccessResult groupCreateResult = null;
		try {
			groupCreateResult = rongCloud.group.create(groupCreateUserId, groupId, groupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupCreateResult.getCode();
	}
	
	/**
	 * 推送添加好友的系统消息
	 * @param messagePublishSystemToUserId
	 * @param userId
	 * @param contactNtfMessage
	 * @return
	 */
	public Integer publishSystem(String[] messagePublishSystemToUserId,String userId,ContactNtfMessage contactNtfMessage){
		CodeSuccessResult messagePublishSystemResult = null;
		try {
			messagePublishSystemResult = rongCloud.message.PublishSystem(userId, messagePublishSystemToUserId,
					contactNtfMessage, "", "", 0, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return messagePublishSystemResult.getCode();
	}
	
	
	/**
	 * 推送自定义系统消息
	 * 
	 * @return
	 */
	public static CodeSuccessResult pushGroupMessage(String fromUserId , String[] toUserId, BaseMessage message, String pushContent) {
		System.out.println(message.toString());
		CodeSuccessResult result = null;
		try {
			result = rongCloud.message.PublishSystem(fromUserId, toUserId,message,pushContent, "{\"pushData\":\"\"}", 1, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("PublishSystem:  " + result.toString());
		return result;
	}
	

	/**
	 * 发送群组消息给微信用户
	 * 
	 * @param groups
	 * @param txtMessage  文本消息
	 * @param operatorUserId
	 * @return
	 */
	public int pushGroupMessage(String[] groups, TxtMessage txtMessage,
			String operatorUserId) {
		CodeSuccessResult messagePublishSystemResult = null;
		try {
			messagePublishSystemResult = rongCloud.message.publishGroup(operatorUserId, groups,txtMessage,
					"thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("PublishSystem:  " + messagePublishSystemResult.toString());
		return messagePublishSystemResult.getCode();
	}
	/**
	 * 发送群组图片消息给微信用户
	 * 
	 * @param groups
	 * @param imgMessage  图片
	 * @param operatorUserId
	 * @return
	 */
	public int pushGroupMessage(String[] groups, ImgMessage imgMessage,
			String operatorUserId) {
		CodeSuccessResult messagePublishSystemResult = null;
		try {
			messagePublishSystemResult = rongCloud.message.publishGroup(operatorUserId, groups,imgMessage,
					"thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("PublishSystem:  " + messagePublishSystemResult.toString());
		return messagePublishSystemResult.getCode();
	}
	
	/**
	 * 给群组发送语音消息
	 * @param groups
	 * @param voiceMessage
	 * @param operatorUserId
	 * @return
	 */
	public  int pushGroupMessage(String[] groups, VoiceMessage voiceMessage,
			String operatorUserId) {
		CodeSuccessResult messagePublishSystemResult = null;
		try {
			messagePublishSystemResult = rongCloud.message.publishGroup(operatorUserId, groups,voiceMessage,
					"thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("PublishSystem:  " + messagePublishSystemResult.toString());
		return messagePublishSystemResult.getCode();
	}
	/**
	 * 给群组发送图文消息
	 * @param groups
	 * @param imgTextMessage
	 * @param operatorUserId
	 * @return
	 */
	public int pushGroupMessage(String[] groups, ImgTextMessage imgTextMessage,
			String operatorUserId) {
		CodeSuccessResult messagePublishSystemResult = null;
		try {
			messagePublishSystemResult = rongCloud.message.publishGroup(operatorUserId, groups,imgTextMessage,
					"thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("PublishSystem:  " + messagePublishSystemResult.toString());
		return messagePublishSystemResult.getCode();
	}
	
	/**
	 * 获取融云token
	 * @param userId
	 * @param realName
	 * @return
	 */
	public static TokenResult getRongCloudToken(String userId,String realName){
		TokenResult userGetTokenResult = null;
		try {
			userGetTokenResult = rongCloud.user.getToken(userId, realName, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userGetTokenResult;
	}
	
	
	/**
	 * 推送在线状态给在线用户
	 * 
	 * @param members
	 * @param profileNtfMessage
	 * @param message
	 * @param userId
	 */
	public int pushOnlineStatus(String[] members, ProfileNtfMessage profileNtfMessage,
			String groupId) {
		CodeSuccessResult messagePublishSystemResult = null;
		try {
			messagePublishSystemResult = rongCloud.message.PublishSystem(groupId, members, profileNtfMessage,
					null, null, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		loger.info(profileNtfMessage);
		loger.info("PushOnlineStatus:  " + messagePublishSystemResult.toString());
		return messagePublishSystemResult.getCode();
	} 

	
}
