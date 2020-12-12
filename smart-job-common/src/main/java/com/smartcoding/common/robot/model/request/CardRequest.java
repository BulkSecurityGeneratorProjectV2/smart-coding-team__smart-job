package com.smartcoding.common.robot.model.request;

import com.smartcoding.common.robot.enums.NotifyMsgTypeEnum;
import com.smartcoding.common.robot.enums.NotifyRobotEnum;
import com.smartcoding.common.robot.model.message.dingding.DingFeedCardMessage;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CardRequest extends BaseNotifyRequest implements RobotNotifyRequest {

    private List<DingFeedCardMessage.LinksBean> links;

    public CardRequest(String webHook, NotifyRobotEnum notifyRobotEnum) {
        super(webHook, notifyRobotEnum);
    }

    @Override
    public NotifyMsgTypeEnum getMsgType() {
        return NotifyMsgTypeEnum.FEED_CARD;
    }
}
