package com.smartcoding.schedule.core.alarm.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.smartcoding.common.robot.enums.NotifyRobotEnum;
import com.smartcoding.common.robot.model.request.TextRequest;
import com.smartcoding.common.robot.model.response.RobotNotifyRequestBuilder;
import com.smartcoding.common.robot.model.response.RobotNotifyResponse;
import com.smartcoding.common.robot.notify.RobotNotify;
import com.smartcoding.common.core.domain.entity.SysUser;
import com.smartcoding.schedule.core.alarm.*;
import com.smartcoding.schedule.core.alarm.*;
import com.smartcoding.schedule.core.exception.AlarmFailException;
import com.smartcoding.schedule.core.model.XxlAlarmInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
@Order(1)
public class DingdingJobAlarm extends AbstractJobAlarm {

    @Override
    public AlarmResult sendAlarm(XxlAlarmInfo alarmInfo, List<SysUser> noticeUserList, String sendContent) throws AlarmFailException {
        String alarmUrl = alarmInfo.getAlarmUrl();
        if (StrUtil.isEmpty(alarmUrl)) {
            return new DefaultFailAlarmResult("钉钉告警地址为空，发送失败");
        }
        try {
            List<String> mobileList = getMobileList(noticeUserList);
            boolean atAll = CollectionUtil.isEmpty(mobileList);
            TextRequest textRequest = RobotNotifyRequestBuilder
                    .textMessageRequest(alarmUrl, NotifyRobotEnum.DINGDING)
                    .setText(sendContent)
                    .setAtAll(atAll)
                    .setAtMobileList(mobileList);
            RobotNotifyResponse notifyResponse = RobotNotify.defaultRobotNotify().sendRobotSyncNotify(textRequest);
            return new DefaultAlarmResult(notifyResponse.isSuccess(), notifyResponse.getErrorMsg());
        } catch (Exception e) {
            throw new AlarmFailException("钉钉通知发送失败", e);
        }
    }

    @Override
    public boolean support(JobAlarmEnum jobAlarmEnum) {
        return JobAlarmEnum.DING_DING.equals(jobAlarmEnum);
    }
}
