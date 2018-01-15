package com.linesum.inventory.infrastructure.persistence.repository;

import com.linesum.inventory.domain.model.order.*;
import com.linesum.inventory.domain.repository.ContactRepository;
import com.linesum.inventory.domain.model.store.Goods;
import com.linesum.inventory.domain.model.store.SkuCode;
import com.linesum.inventory.domain.repository.OrderRepository;
import com.linesum.inventory.infrastructure.persistence.jpa.GoodsRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.OrderGoodsMiddleRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.jpa.OrderRepositoryJpa;
import com.linesum.inventory.infrastructure.persistence.mapper.GoodsMapper;
import com.linesum.inventory.infrastructure.persistence.mapper.OrderGoodsMiddleMapper;
import com.linesum.inventory.infrastructure.persistence.mapper.OrderMapper;
import com.linesum.inventory.infrastructure.persistence.po.GoodsPo;
import com.linesum.inventory.infrastructure.persistence.po.OrderGoodsMiddlePo;
import com.linesum.inventory.infrastructure.persistence.po.OrderPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by zhengjx on 2017/11/2.
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderRepositoryJpa orderRepositoryJpa;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderGoodsMiddleRepositoryJpa orderGoodsMiddleRepositoryJpa;

    @Autowired
    private OrderGoodsMiddleMapper orderGoodsMiddleMapper;

    @Autowired
    private GoodsRepositoryJpa goodsRepositoryJpa;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ContactRepository contactRepository;


    @Override
    public Order find(OrderId orderId) {
//        OrderPo orderPo = orderMapper.selectByPrimaryKey(orderId.getId());
        OrderPo orderPo = orderRepositoryJpa.findOne(orderId.getId());
//        List<OrderGoodsMiddlePo> orderGoodsMiddlePoList = orderGoodsMiddleMapper.findByOrderId(orderId.getId());
        List<OrderGoodsMiddlePo> orderGoodsMiddlePoList = orderGoodsMiddleRepositoryJpa.findByOrderId(orderId.getId());

        Contact sender = contactRepository.find(new ContactId(orderPo.getSenderId()));
        Contact acceptor = contactRepository.find(new ContactId(orderPo.getAcceptorId()));

        List<Goods> goodsList = orderGoodsMiddlePoList.stream()
                .map(ogmPo -> {
                    GoodsPo goodsPo = goodsMapper.selectByPrimaryKey(ogmPo.getGoodsId());
//                    GoodsPo goodsPo = goodsRepositoryJpa.findOne(ogmPo.getGoodsId());
                    return new Goods(
                            new SkuCode(goodsPo.getSkuCode()),
                            ogmPo.getQty(),
                            goodsPo.getPrice()
                    );
                })
                .collect(Collectors.toList());

        return new Order(
                orderId,
                acceptor,
                sender,
                goodsList,
                orderPo.getSendDate()
        );
    }

    @Override
    public OrderId save(Order order) {
        OrderPo orderPo = orderRepositoryJpa.save(new OrderPo(
//        OrderPo orderPo = new OrderPo(
                null,
                order.getSender().getContactId().getId(),
                order.getAcceptor().getContactId().getId(),
                order.getSendDate(),
                null
        ));
//        orderMapper.insert(orderPo);

        List<String> skuCodeList = order.getOrderGoodsList().stream()
                .map(goods -> goods.getSkuCode().getCode())
                .collect(Collectors.toList());

//        List<GoodsPo> goodsPoList = goodsMapper.findBySkuCodeIn(skuCodeList);
        List<GoodsPo> goodsPoList = goodsRepositoryJpa.findBySkuCodeIn(skuCodeList);

//        order.getOrderGoodsList().forEach(goods -> orderGoodsMiddleMapper.insert(new OrderGoodsMiddlePo(
        order.getOrderGoodsList().forEach(goods -> orderGoodsMiddleRepositoryJpa.save(new OrderGoodsMiddlePo(
                null,
                orderPo.getId(),
                goodsPoList.stream()
                        .filter(goodsPo -> Objects.equals(goodsPo.getSkuCode(), goods.getSkuCode().getCode()))
                        .findFirst()
                        .get().getId(),
                goods.getQty()
        )));

        return new OrderId(orderPo.getId());
    }
}
