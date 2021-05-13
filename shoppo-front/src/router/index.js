import Vue from "vue";
import VueRouter from "vue-router";
import LoginLayout from "@/layouts/LoginLayout.vue";
import GeneralLoginView from "@/views/login/GeneralLoginView.vue";
import AdminLayout from "@/layouts/AdminLayout.vue";
import AddStoreView from "@/views/admin/AddStoreView.vue";
import Login from "@/components/login/Login";
import Inscription from "@/components/login/Inscription";
import ResetPassword from "@/components/login/ResetPassword";

import CommerceLayout from "@/layouts/CommerceLayout";
import ClientLayout from "@/layouts/ClientLayout";

import store from "../store/index.js";
import AddProductView from "@/views/store/product/AddProductView";
import EditProductView from "@/views/store/product/EditProductView";

import GestionStock from "@/views/store/product/GestionStock";
import EditStoreView from "@/views/store/EditStoreView";
import ViewCommerce from "@/views/client/ViewCommerce";
import AllCommercesView from "@/views/client/AllCommercesView";
import GestionCommandes from "@/views/store/commande/GestionCommandes";
import CommandesClient from "@/views/client/CommandesClient";
import DetailCommandeCommerce from "@/views/store/commande/DetailCommandeCommerce";
import DetailCommandeClient from "@/views/client/DetailCommandeClient";
import ShoppingCartView from "@/views/client/ShoppingCartView";
import QrCodeCardView from "@/views/client/QrCodeCardView";
import ReadQRCodeView from "@/views/store/ReadQRCodeView";
import CreateCommandView from "@/views/store/CreateCommandView";
import QrCodeCommande from "@/views/store/QrCodeCommande";
import VFPClientView from "@/views/client/VFPClientView";
import MyProfileView from "@/views/client/MyProfileView";
import RechargeSoldeView from "@/views/client/RechargeSoldeView";
import GestionVfp from "@/views/store/vfp/GestionVfp";
import AddProductVfp from "@/views/store/vfp/AddProductVfp";
import PickUpCommandesView from "@/views/client/PickUpCommandesView";
import PickUpCommandesMapView from "@/views/client/PickUpCommandesMapView";
import StatisticsView from "@/views/store/StatisticsView";
import StatisticsAdminView from "@/views/admin/StatisticsAdminView";


Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    redirect: "login",
    component: LoginLayout,
    children: [
      {
        path: "/login",
        name: "generalLogin",
        component: GeneralLoginView,
        children: [
          {
            path: "/login",
            name: "login",
            component: Login
          },
          {
            path: "/inscription",
            name: "inscription",
            component: Inscription
          },
          {
            path: "/resetPassword",
            name: "resetPassword",
            component: ResetPassword
          }
        ]
      }
    ]
  },
  {
    path: "/admin",
    component: AdminLayout,
    redirect: "/admin/statistics",
    children: [
      {
        path: "/admin/addStore",
        name: "addStore",
        component: AddStoreView
      },
      {
        path: "/admin/statistics",
        name: "statisticsAdmin",
        component: StatisticsAdminView
      }
    ]
  },
  {
    path: "/commerce",
    component: CommerceLayout,
    redirect: "/commerce/statistics",
    children: [
      {
        path: "/commerce/edit",
        name: "editStore",
        component: EditStoreView
      },
      {
        path: "/commerce/product/add",
        name: "addProduct",
        component: AddProductView
      },
      {
        path: "/commerce/product/edit",
        name: "editProduct",
        component: EditProductView
      },
      {
        path: "/commerce/product/gestion-stock",
        name: "gestionStock",
        component: GestionStock
      },
      {
        path: "/commerce/commandes",
        name: "commandes",
        component: GestionCommandes
      },
      {
        path: "/commerce/commandes/detail-commande",
        name: "DetailCommandeCommerce",
        component: DetailCommandeCommerce
      },
      {
        path: "/commerce/qrcode",
        name: "readQRCode",
        component: ReadQRCodeView
      },
      {
        path: "/commerce/command/create",
        name: "createCommand",
        component: CreateCommandView
      },
      {
        path: "/commerce/command/qrcode",
        name: "qrCodeCommand",
        component: QrCodeCommande
      },
      {
        path: "/commerce/vfp",
        name: "gestionVfp",
        component: GestionVfp
      },
      {
        path: "/commerce/vfp/addvfp",
        name: "ajouterVfp",
        component: AddProductVfp
      },
      {
        path: "/commerce/statistics",
        name: "Statistics",
        component: StatisticsView
      }
    ]
  },
  {
    path: "/client",
    component: ClientLayout,
    redirect: "/client/commerce",
    children: [
      {
        path: "/client/commerce/view",
        name: "viewCommerce",
        component: ViewCommerce
      },
      {
        path: "/client/commerce",
        name: "AllCommerce",
        component: AllCommercesView
      },
      {
        path: "/client/commandes",
        name: "commandesClient",
        component: CommandesClient
      },
      {
        path: "/client/commandes/detail-commande",
        name: "DetailCommandeClient",
        component: DetailCommandeClient
      },
      {
        path: "/client/cart",
        name: "ShoppingCart",
        component: ShoppingCartView
      },
      {
        path: "/client/card",
        name: "QrCodeCard",
        component: QrCodeCardView
      },
      {
        path: "/client/vfp",
        name: "VFP",
        component: VFPClientView
      },
      {
        path: "/client/profil",
        name: "VFP",
        component: MyProfileView
      },
      {
        path: "/client/solde",
        name: "Solde",
        component: RechargeSoldeView
      },
      {
        path: "/client/pickup",
        name: "PickUpCommandes",
        component: PickUpCommandesView
      },
      {
        path: "/client/pickup/map",
        name: "PickUpCommandesMap",
        component: PickUpCommandesMapView
      }
    ]
  },
  {
    path: "/logout",
    redirect: "/login"
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  const isLogin = /login/.test(to.path);
  const isRegister = /inscription/.test(to.path);
  const isResetPassword = /resetPassword/.test(to.path);
  if (store.state.userStore != null) {
    if (/client/.test(to.path)) {
      if (store.state.userStore.role === "CLIENT") {
        next();
      } else {
        next("/login");
      }
    } else if (/admin/.test(to.path)) {
      if (store.state.userStore.role === "ADMIN") {
        next();
      } else {
        next("/login");
      }
    } else if (/commerce/.test(to.path)) {
      if (store.state.userStore.role === "COMMERCANT") {
        next();
      } else {
        next("/login");
      }
    } else {
      next();
    }
  } else {
    console.log("il est null !!!!!!");
    if (isLogin || isRegister || isResetPassword) {
      next();
    } else {
      next("/login");
    }
  }
});

export default router;
