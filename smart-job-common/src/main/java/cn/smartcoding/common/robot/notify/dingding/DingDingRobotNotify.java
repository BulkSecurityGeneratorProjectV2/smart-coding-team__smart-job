package cn.smartcoding.common.robot.notify.dingding;


import cn.hutool.core.util.StrUtil;
import cn.smartcoding.common.robot.enums.NotifyRobotEnum;
import cn.smartcoding.common.robot.model.message.Message;
import cn.smartcoding.common.robot.model.request.RobotNotifyRequest;
import cn.smartcoding.common.robot.model.response.RobotNotifyResponse;
import cn.smartcoding.common.robot.notify.AbstractRobotNotify;
import cn.smartcoding.common.robot.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

/**
 * 钉钉机器人通知service
 *
 * @author qingshi
 * @email 705029004@qq.com
 * @date 2020/02/06- 8:26 下午
 */
@Slf4j
public class DingDingRobotNotify extends AbstractRobotNotify {

    @Override
    public RobotNotifyResponse sendRobotNotify(RobotNotifyRequest robotNotifyRequest) {
        String webHook = robotNotifyRequest.getWebHook();
        if (StrUtil.isBlank(webHook)) {
            return RobotNotifyResponse.error("调用钉钉机器人, Webhook is empty");
        }
        Message message = buildMessage(robotNotifyRequest);
        if (null == message) {
            return RobotNotifyResponse.error("钉钉暂时不支持消息类型:" + robotNotifyRequest.getMsgType());
        }
        RobotNotifyResponse robotNotifyResponse = new RobotNotifyResponse();
        String param = message.toJsonString();
        try {
            HttpResponse response = HttpUtils.sendPost(webHook, param);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return buildRobotNotifyResponse(response);
            }
        } catch (Exception e) {
            robotNotifyResponse = RobotNotifyResponse.error(e.getMessage());
        }
        log.info("send param:{}", param);
        return robotNotifyResponse;
    }


    @Override
    public NotifyRobotEnum getNotifyRobotEnum() {
        return NotifyRobotEnum.DINGDING;
    }

    @Override
    public Message buildMessage(RobotNotifyRequest robotNotifyRequest) {
        return DingDingMessageCovert.buildMessage(robotNotifyRequest);
    }
}
