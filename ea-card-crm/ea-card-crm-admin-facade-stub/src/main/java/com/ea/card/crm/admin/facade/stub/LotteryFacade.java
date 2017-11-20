package com.ea.card.crm.admin.facade.stub;

import com.ea.card.crm.admin.facade.request.LotteryPageRequest;
import com.ea.card.crm.admin.facade.request.LotteryRequest;
import com.ea.card.crm.admin.facade.response.LotteryPageResponse;
import com.ea.card.crm.admin.facade.response.LotteryResponse;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ea-card-crm-admin")
@RequestMapping(value = "/lotteryProduct")
public interface LotteryFacade {

    @RequestMapping(value = "/getLotteryOfPage", method = RequestMethod.POST)
    LotteryPageResponse getLotteryOfPage(@RequestBody LotteryPageRequest request);

    @RequestMapping(value = "/getLottery", method = RequestMethod.POST)
    LotteryResponse getLottery(@RequestBody StringRequest request);

    @RequestMapping(value = "/removeLottery", method = RequestMethod.POST)
    NormalResponse removeLottery(@RequestBody StringRequest request);

    @RequestMapping(value = "/addLottery", method = RequestMethod.POST)
    StringResponse addLottery(@RequestBody LotteryRequest request);

    @RequestMapping(value = "/editLottery", method = RequestMethod.POST)
    StringResponse editLottery(@RequestBody LotteryRequest request);
}
