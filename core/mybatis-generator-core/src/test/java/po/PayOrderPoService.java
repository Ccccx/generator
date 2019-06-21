package po;

import dao.PayOrderMapper;
import dto.PayOrder;
import exampleModel.PayOrderExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* Add by Mybatis3 generator cx.
* 2019-06-21 14:06:05
*/
@Service
public class PayOrderPoService {
    @Resource
    private PayOrderMapper payOrderMapper;

    public PayOrder selectByPrimaryKey(Long id) {
        return payOrderMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(PayOrder record) {
        return payOrderMapper.updateByPrimaryKeySelective(record);
    }

    public List<PayOrder> queryPayOrder(String payOrderNo) {
        return queryPayOrder(payOrderNo, null, null);
    }

    public List<PayOrder> queryPayOrder(String payOrderNo, String payChannelCode) {
        return queryPayOrder(payOrderNo, payChannelCode, null);
    }

    public List<PayOrder> queryPayOrder(String payOrderNo, String payChannelCode, Short payState) {
        PayOrderExample e = new PayOrderExample();
        PayOrderExample.Criteria criteria = e.createCriteria();
        
        if (payOrderNo != null) { criteria.andPayOrderNoEqualTo(payOrderNo);}
        
        if (payChannelCode != null) { criteria.andPayChannelCodeEqualTo(payChannelCode);}
        
        if (payState != null) { criteria.andPayStateEqualTo(payState);}
        
        return payOrderMapper.selectByExample(e);
    }

    public List<PayOrder> queryPayChannel(String payChannelCode) {
        return queryPayChannel(payChannelCode, null);
    }

    public List<PayOrder> queryPayChannel(String payChannelCode, Short payState) {
        PayOrderExample e = new PayOrderExample();
        PayOrderExample.Criteria criteria = e.createCriteria();
        
        if (payChannelCode != null) { criteria.andPayChannelCodeEqualTo(payChannelCode);}
        
        if (payState != null) { criteria.andPayStateEqualTo(payState);}
        
        return payOrderMapper.selectByExample(e);
    }

    public List<PayOrder> updatePayOrderByCondition(PayOrder dto, String payOrderNo, String payChannelCode, Short payState) {
        PayOrderExample e = new PayOrderExample();
        PayOrderExample.Criteria criteria = e.createCriteria();
        
        if (payOrderNo != null) { criteria.andPayOrderNoEqualTo(payOrderNo);}
        
        if (payChannelCode != null) { criteria.andPayChannelCodeEqualTo(payChannelCode);}
        
        if (payState != null) { criteria.andPayStateEqualTo(payState);}
        
        payOrderMapper.updateByExampleSelective(dto, e);
        return payOrderMapper.selectByExample(e);
    }

    public void updatePayChannelByCondition(PayOrder dto, String payChannelCode, Short payState) {
        PayOrderExample e = new PayOrderExample();
        PayOrderExample.Criteria criteria = e.createCriteria();
        
        if (payChannelCode != null) { criteria.andPayChannelCodeEqualTo(payChannelCode);}
        
        if (payState != null) { criteria.andPayStateEqualTo(payState);}
        
        payOrderMapper.updateByExampleSelective(dto, e);
    }
}