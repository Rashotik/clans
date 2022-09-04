package com.example.clans.servlets;

import com.example.clans.model.History;
import com.example.clans.model.Transfer;
import com.example.clans.service.ClanService;
import com.example.clans.service.ClanServiceImpl;
import com.example.clans.service.TransferService;
import com.example.clans.service.TransferServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/history")
public class ClanTransferHistoryServlet extends HttpServlet {
    private TransferService transferService;
    private ClanService clanService;

    public void init() {
        transferService = new TransferServiceImpl();
        clanService = new ClanServiceImpl();
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String entity = request.getParameter("entity");
        if((id == null && name == null) || entity == null){
            response.setStatus(403);
            return;
        }
        List<History> transfers = null;
        if(id == null){
            transfers = transferService.getTransfersByEntityName(name, entity);
        }
        if(transfers == null){
            response.setStatus(403);
            return;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.write(transfers.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}