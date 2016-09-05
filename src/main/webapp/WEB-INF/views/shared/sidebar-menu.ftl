<aside class="sidebar-menu fixed">
    <div class="sidebar-inner scrollable-sidebar">
        <div class="main-menu">
            <ul class="accordion" id="sidebar">
                <li class="menu-header">
                    Main Menu
                </li>
                <li class="bg-palette1 active">
                    <a href="/">
                        <span class="menu-content block">
                            <span class="menu-icon"><i class="block fa fa-home fa-lg"></i></span>
                            <span class="text m-left-sm">首页</span>
                        </span>
                    </a>
                </li>
                <li class="openable bg-palette2">
                    <a href="#">
                        <span class="menu-content block">
                            <span class="menu-icon"><i class="block fa fa-users fa-lg"></i></span>
                            <span class="text m-left-sm">用户管理</span>
                            <span class="submenu-icon"></span></span>
                        <span class="menu-content-hover block">Menu</span>
                    </a>
                    <ul class="submenu">
                        <li>
                            <a href="[@spring.url '/user/pagination'/]">
                                <span class="submenu-label">用户管理</span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="bg-palette3">
                    <a href="[@spring.url '/report/pagination'/]">
                        <span class="menu-content block">
                            <span class="menu-icon"><i class="block fa fa-bullhorn fa-lg"></i></span>
                             <span class="submenu-label">举报管理</span>
                        </span>
                    </a>
                </li>
                <li class="bg-palette4">
                    <a href="[@spring.url '/feed_back/pagination'/]">
                        <span class="menu-content block">
                            <span class="menu-icon"><i class="block fa fa-circle fa-lg"></i></span>
                             <span class="submenu-label">意见反馈</span>
                        </span>
                    </a>
                </li>
                <li class="bg-palette1">
                    <a href="[@spring.url '/money_detailed/pagination'/]">
                        <span class="menu-content block">
                            <span class="menu-icon"><i class="block fa fa-money fa-lg"></i></span>
                             <span class="submenu-label">资金明细</span>
                        </span>
                    </a>
                </li>
                <li class="bg-palette1">
                    <a href="[@spring.url '/withdraw/pagination'/]">
                        <span class="menu-content block">
                            <span class="menu-icon"><i class="block fa fa-money fa-lg"></i></span>
                             <span class="submenu-label">提现管理</span>
                        </span>
                    </a>
                </li>
                <li class="bg-palette1">
                    <a href="[@spring.url '/article/pagination'/]">
                        <span class="menu-content block">
                            <span class="menu-icon"><i class="block fa fa-money fa-lg"></i></span>
                             <span class="submenu-label">活动管理</span>
                        </span>
                    </a>
                </li>
                <li class="bg-palette2">
                    <a href="[@spring.url '/recharge/pagination'/]">
                        <span class="menu-content block">
                            <span class="menu-icon"><i class="block fa fa-money fa-lg"></i></span>
                             <span class="submenu-label">充值记录</span>
                        </span>
                    </a>
                </li>
                <li class="openable bg-palette4">
                    <a href="#">
                        <span class="menu-content block">
                            <span class="menu-icon"><i class="block fa fa-list fa-lg"></i></span>
                            <span class="text m-left-sm">系统设置</span>
                            <span class="submenu-icon"></span></span>
                        <span class="menu-content-hover block">Menu</span>
                    </a>
                    <ul class="submenu">
                        <li>
                            <a href="[@spring.url '/robot/create'/]">
                                <span class="submenu-label">机器人添加</span>
                            </a>
                        </li>
                        <li>
                            <a href="[@spring.url '/game_multiple/pagination'/]">
                                <span class="submenu-label">游戏倍数管理</span>
                            </a>
                        </li>
                        <li>
                            <a href="[@spring.url '/area/pagination'/]">
                                <span class="submenu-label">区域管理</span>
                            </a>
                        </li>
                        <li>
                            <a href="[@spring.url '/permission/pagination'/]">
                                <span class="submenu-label">权限管理</span>
                            </a>
                        </li>
                        <li>
                            <a href="[@spring.url '/role/pagination'/]">
                                <span class="submenu-label">角色管理</span>
                            </a>
                        </li>
                        <li>
                            <a href="[@spring.url '/account/pagination'/]">
                                <span class="submenu-label">账号管理</span>
                            </a>
                        </li>
                        <li>
                            <a href="[@spring.url '/app_Version/pagination'/]">
                                <span class="submenu-label">版本管理</span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="bg-palette1">
                    <a href="[@spring.url '/logout'/]">
                        <span class="menu-content block">
                            <span class="menu-icon"><i class="block fa ion-log-out fa-lg"></i></span>
                            <span class="text m-left-sm">退出</span>
                        </span>
                    </a>
                </li>
            </ul>
        </div>
    </div><!-- sidebar-inner -->
</aside>