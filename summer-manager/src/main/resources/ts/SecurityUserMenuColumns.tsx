import {Space} from "antd";
import {ColumnsType} from "antd/es/table";
import {Link} from "react-router-dom";
import {SecurityUserMenu} from "type/entity/SecurityUserMenu";
import {columnDataIndex, columnOrder, columnRemark} from "./index";


const securityUserMenuColumns: ColumnsType<SecurityUserMenu> = [
    columnDataIndex,
    {
        key: 'id',
        dataIndex: 'id',
        title: '自增主建',
        width: 110,
        align: "center",
    },
    {
        key: 'key',
        dataIndex: 'key',
        title: '此数据唯一识别码',
        width: 110,
        align: "center",
    },
    {
        key: 'userId',
        dataIndex: 'userId',
        title: '用户id',
        width: 110,
        align: "center",
    },
    {
        key: 'menuId',
        dataIndex: 'menuId',
        title: '菜单id',
        width: 110,
        align: "center",
    },
    {
        key: 'createTime',
        dataIndex: 'createTime',
        title: '创建时间',
        width: 110,
        align: "center",
    },
    {
        key: 'version',
        dataIndex: 'version',
        title: '数据版本',
        width: 110,
        align: "center",
    },
    {
        key: 'delete',
        dataIndex: 'delete',
        title: '逻辑删除字段',
        width: 110,
        align: "center",
    },
    {
        key: 'remark',
        dataIndex: 'remark',
        title: '备注信息',
        width: 110,
        align: "center",
    },
    columnOrder,
    columnRemark,

    {
        key: "options",
        title: "操作",
        dataIndex: "options",
        align: "center",
        width: 130,
        fixed: "right",
        render: (_, record, __) => {
            return <Space size={15}>
                <Link key={"securityUserMenu-details"} to={"details"} state={record}>详情</Link>
                <Link key={"securityUserMenu-update"} to={"update"} state={record}>更新</Link>
                <Link key={"securityUserMenu-delete"} style={{color: "#c13c3c"}} to={"delete"} state={record}>删除</Link>
            </Space>;
        }
    }

];
