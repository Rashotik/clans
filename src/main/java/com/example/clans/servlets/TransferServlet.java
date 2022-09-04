package com.example.clans.servlets;

import com.example.clans.model.Transfer;
import com.example.clans.service.TransferService;
import com.example.clans.service.TransferServiceImpl;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
    private TransferService transferService;

    public void init() {
        transferService = new TransferServiceImpl();
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(LocalDateTime.now());
        System.out.println(Date.valueOf(LocalDate.now()));
            long id;
            try {
                id = Long.parseLong(request.getParameter("id"));
            }catch (NumberFormatException e){
                e.printStackTrace();
                response.setStatus(403);
                return;
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");

            try {
                PrintWriter out = response.getWriter();
                Transfer transfer = transferService.getTransferById(id);
                if (transfer == null){
                    response.setStatus(403);
                }
                else
                    out.println(transfer);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        if(request.getParameter("fromId") == null ||
                request.getParameter("toId") == null ||
                request.getParameter("gold") == null ||
                request.getParameter("sentFrom") == null ||
                request.getParameter("sentTo") == null){
            response.setStatus(403);
            return;
        }
        System.out.println(request.getParameter("sentTo"));
        System.out.println(Long.parseLong(request.getParameter("fromId")));
        Transfer transfer = new Transfer();
        try {
            transfer.setFromId(Long.parseLong(request.getParameter("fromId")));
            transfer.setToId(Long.parseLong(request.getParameter("toId")));
            transfer.setGold(BigDecimal.valueOf(Double.parseDouble(request.getParameter("gold"))));
            transfer.setSentFrom(request.getParameter("sentFrom"));
            transfer.setSentTo(request.getParameter("sentTo"));
            System.out.println(transfer.getSentTo());
        }catch (NumberFormatException e){
            e.printStackTrace();
            response.setStatus(403);
            return;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter out = response.getWriter();
            if(transferService.transferGoldAndSaveCheck(transfer)){
                out.println(true);
            } else{
                response.setStatus(403);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}