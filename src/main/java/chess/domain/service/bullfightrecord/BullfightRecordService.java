package chess.domain.service.bullfightrecord;

import chess.domain.model.user.IUserRepository;
import chess.domain.model.user.User;
import chess.domain.service.user.IUserService;
import chess.game.bullfight.command.GameOver;
import chess.game.bullfight.mode.Seat;
import chess.game.bullfight.push.LeavePushObject;
import chess.game.bullfight.take.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyp on 2016/7/20.
 *
 */
@Service("bullfightRecordService")
public class BullfightRecordService implements IBullfightRecordService {

    private final IUserRepository<User, String> iUserRepository;

    private final IUserService userService;

    @Autowired
    public BullfightRecordService(IUserRepository<User, String> iUserRepository, IUserService userService) {
        this.iUserRepository = iUserRepository;
        this.userService = userService;
    }


    //游戏账目结算
    @Override
    public List<GameOver> gameSettlement(List<GameOver> over, Game game) {
        int seatNo = -1;//庄家的座位号
        User BankerUser = null;
        //拿到庄家的座位号
        for (Seat seat : game.getDesk().getSeats()) {
            if (seat.getIsTheBanker().getValue() == 1) {
                seatNo = seat.getSeatNo();
                BankerUser = userService.searchByName(seat.getPlayer().getUserName());
                break;
            }
        }
        List<GameOver> gameOverList = new ArrayList<>();
        assert BankerUser != null;
        double money = BankerUser.getMoney().doubleValue();
        for (GameOver gameOver : over) {
            if (gameOver.getSeatNo() != seatNo) {
                //得到庄家赢得钱
                if (gameOver.getScore().intValue() < 0) {

                    for (Seat seat : game.getDesk().getSeats()) {
                        if (seat.getSeatNo() == gameOver.getSeatNo()) {
                            User user = userService.searchByName(seat.getPlayer().getUserName());
                            BigDecimal bigDecimal;
                            double a;//闲家输的钱
                            if (user.getMoney().intValue() + gameOver.getScore().intValue() >= 0) {
                                bigDecimal = new BigDecimal(user.getMoney().doubleValue() + gameOver.getScore().intValue());
                                money = money - gameOver.getScore().intValue();
                                a = gameOver.getScore().intValue();
                            } else {
                                bigDecimal = new BigDecimal(0);
                                money = money + user.getMoney().intValue();
                                a = -user.getMoney().intValue();
                            }
                            user.changeMoney(bigDecimal);
                            iUserRepository.update(user);
                            GameOver gameOver1 = new GameOver();
                            gameOver1.setCardType(gameOver.getCardType());
                            gameOver1.setSeatNo(seat.getSeatNo());
                            gameOver1.setScore(new BigDecimal(a));
                            gameOverList.add(gameOver1);
                        }
                    }
                }
            }

        }
        int sum = 0;//庄家总共输的钱
        for (GameOver gameOver : over) {
            if (gameOver.getSeatNo() != seatNo) {
                if (gameOver.getScore().intValue() > 0) {
                    sum += gameOver.getScore().intValue();
                }
            }

        }
        if (sum != 0) {
            if (money - sum < 0) {
                //庄家的钱不够赔
                for (GameOver gameOver : over) {
                    if (gameOver.getScore().intValue() > 0) {
                        if (gameOver.getSeatNo() != seatNo) {
                            for (Seat seat : game.getDesk().getSeats()) {
                                BigDecimal bigDecimal;
                                double b;//闲家赢得钱
                                if (gameOver.getSeatNo() == seat.getSeatNo()) {
                                    User user = userService.searchByName(seat.getPlayer().getUserName());
                                    bigDecimal = new BigDecimal(user.getMoney().doubleValue() + (money * (gameOver.getScore().intValue() / sum)));
                                    b = money * (gameOver.getScore().intValue() / sum);
                                    user.changeMoney(bigDecimal);
                                    iUserRepository.update(user);
                                    GameOver gameOver1 = new GameOver();
                                    gameOver1.setCardType(gameOver.getCardType());
                                    gameOver1.setSeatNo(seat.getSeatNo());
                                    gameOver1.setScore(new BigDecimal(b));
                                    gameOverList.add(gameOver1);
                                }
                            }
                        }
                    }
                }
            } else {
                //庄家的钱够赔
                for (GameOver gameOver : over) {
                    if (gameOver.getScore().intValue() > 0) {
                        if (gameOver.getSeatNo() != seatNo) {
                            for (Seat seat : game.getDesk().getSeats()) {
                                BigDecimal bigDecimal;
                                double b;//闲家赢得钱
                                if (gameOver.getSeatNo() == seat.getSeatNo()) {
                                    User user = userService.searchByName(seat.getPlayer().getUserName());
                                    bigDecimal = new BigDecimal(user.getMoney().intValue() + gameOver.getScore().intValue());
                                    b = gameOver.getScore().doubleValue();
                                    user.changeMoney(bigDecimal);
                                    iUserRepository.update(user);
                                    GameOver gameOver1 = new GameOver();
                                    gameOver1.setCardType(gameOver.getCardType());
                                    gameOver1.setSeatNo(seat.getSeatNo());
                                    gameOver1.setScore(new BigDecimal(b));
                                    gameOverList.add(gameOver1);
                                }
                            }
                        }
                    }
                }
            }
        }

        money = money - sum;
        double c;//庄家的输赢
        //计算庄家的余额
        BigDecimal bigDecimal;
        if (money <= 0) {
            bigDecimal = new BigDecimal(0);
            c = -BankerUser.getMoney().intValue();
        } else {
            bigDecimal = new BigDecimal(money);
            c = money - BankerUser.getMoney().intValue();
        }

        BankerUser.changeMoney(bigDecimal);
        iUserRepository.update(BankerUser);
        GameOver gameOver1 = new GameOver();
        for (GameOver gameOver : over) {
            if (gameOver.getSeatNo() == seatNo) {
                gameOver1.setCardType(gameOver.getCardType());
            }
        }
        gameOver1.setSeatNo(seatNo);
        gameOver1.setScore(new BigDecimal(c));
        gameOverList.add(gameOver1);
        return gameOverList;
    }

    //闲家强制退出后的惩罚
    @Override
    public BigDecimal leaveCompensate(Game game, String userName, BigDecimal s_sum) {
        System.out.println(userName);
        BigDecimal compensateGold = null;//赔偿的金币

        User BankerUser = null;
        //拿到庄家的座位号
        for (Seat seat : game.getDesk().getSeats()) {
            if (seat.getIsTheBanker().getValue() == 1) {
                BankerUser = userService.searchByName(seat.getPlayer().getUserName());
            }
        }

        //计算闲家退出后，对庄家的赔偿
        int sum;
        for (Seat seat : game.getDesk().getSeats()) {
            if (null != seat.getPlayer()) {
                if (seat.getPlayer().getUserName().equals(userName)) {
                    User user = userService.searchByName(seat.getPlayer().getUserName());
                    int user_score = user.getMoney().intValue();//闲家的金币
                    BigDecimal bigDecimal;
                    if (user_score - s_sum.intValue() < 0) {
                        //闲家的金币不够赔偿庄家
                        bigDecimal = new BigDecimal(0);
                        compensateGold =new BigDecimal(user_score) ;
                        sum = user_score;
                    } else {
                        bigDecimal = new BigDecimal(user_score - s_sum.intValue());
                        compensateGold = s_sum;
                        sum = s_sum.intValue();

                    }
                    user.changeMoney(bigDecimal);
                    iUserRepository.update(user);
                    assert BankerUser != null;
                    BigDecimal bigDecimalBanker = new BigDecimal(BankerUser.getMoney().intValue() + sum);
                    BankerUser.changeMoney(bigDecimalBanker);
                    iUserRepository.update(BankerUser);
                }
            }
        }
        return compensateGold;
    }

    //庄家强制退出后的惩罚
    @Override
    public List<LeavePushObject> bankerLeaveCompensate(List<LeavePushObject> leavePushObjectList, Game game) {
        //得到庄家
        User BankerUser = null;
        //拿到庄家的座位号
        for (Seat seat : game.getDesk().getSeats()) {
            if (seat.getIsTheBanker().getValue() == 1) {
                BankerUser = userService.searchByName(seat.getPlayer().getUserName());
            }
        }

        double sum = 0;//庄家的赔偿总额
        for (LeavePushObject leave : leavePushObjectList) {
            sum += leave.getMoney().intValue();
        }

        List<LeavePushObject> leavePushObjects = new ArrayList<>();
        //查看庄家的金币是否足够赔偿
        assert BankerUser != null;
        double banker_score = BankerUser.getMoney().doubleValue();//庄家的金币
        if (banker_score - sum < 0) {
            //不够,按照比例赔偿
            for (LeavePushObject leave : leavePushObjectList) {
                for (Seat seat : game.getDesk().getSeats()) {
                    BigDecimal bigDecimal;
                    if (leave.getSeatNo() == seat.getSeatNo()) {
                        User user = userService.searchByName(seat.getPlayer().getUserName());
                        bigDecimal = new BigDecimal(user.getMoney().intValue() + (banker_score * (leave.getMoney().intValue() / sum)));
                        user.changeMoney(bigDecimal);
                        iUserRepository.update(user);
                        LeavePushObject leavePushObject = new LeavePushObject();
                        leavePushObject.setSeatNo(seat.getSeatNo());
                        leavePushObject.setMoney(new BigDecimal(banker_score * (leave.getMoney().intValue()/ sum)));
                        leavePushObjects.add(leavePushObject);
                    }
                }
            }

        } else {
            //够,按照实际赔偿
            for (LeavePushObject leave : leavePushObjectList) {
                for (Seat seat : game.getDesk().getSeats()) {
                    BigDecimal bigDecimal;
                    if (leave.getSeatNo() == seat.getSeatNo()) {
                        User user = userService.searchByName(seat.getPlayer().getUserName());
                        bigDecimal = new BigDecimal(user.getMoney().intValue() + leave.getMoney().intValue());
                        user.changeMoney(bigDecimal);
                        iUserRepository.update(user);
                        LeavePushObject leavePushObject = new LeavePushObject();
                        leavePushObject.setSeatNo(seat.getSeatNo());
                        leavePushObject.setMoney(leave.getMoney());
                        leavePushObjects.add(leavePushObject);
                    }
                }
            }

        }

        banker_score = banker_score - sum;//庄家的剩余
        BigDecimal bigDecimal;
        //计算庄家的余额
        if (banker_score < 0) {
            bigDecimal = new BigDecimal(0);
        } else {
            bigDecimal = new BigDecimal(banker_score);
        }
        BankerUser.changeMoney(bigDecimal);
        iUserRepository.update(BankerUser);
        return leavePushObjects;
    }

    @Override
    public Boolean charge(String userName, int baseScore) {
        User user = iUserRepository.searchByName(userName);
        int value = user.getMoney().intValue();
        Boolean flag = false;
        if (value > baseScore) {
            flag = true;
            BigDecimal decimal = new BigDecimal(value - baseScore);
            user.changeMoney(decimal);
            iUserRepository.update(user);
        }
        return flag;
    }
}
